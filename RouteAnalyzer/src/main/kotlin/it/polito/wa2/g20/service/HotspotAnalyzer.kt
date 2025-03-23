package it.polito.wa2.g20.service

import it.polito.wa2.g20.model.AccurateMostFrequentedArea
import it.polito.wa2.g20.model.MostFrequentedArea
import it.polito.wa2.g20.model.Point
import it.polito.wa2.g20.model.Waypoint


object HotspotAnalyzer {

    fun findAccurateMostVisitedArea(waypoints: List<Waypoint>, radius: Double): AccurateMostFrequentedArea? {
        val radiusM = radius * 1000
        // get resolution from radius
        val res = H3Utils.getResolution(radiusM)
        val cellCount = waypoints.groupingBy {
            H3Utils.geoToH3(it.latitude, it.longitude, res)
        }.eachCount()

        if (cellCount.isEmpty()) {
            return null
        }

        // get the best cell by counting the number of points in the surrounding cells of each cell given the radius
        val kCells = H3Utils.countRings(radiusM, res)
        val densityScores = mutableMapOf<Long, Int>()
        for (cell in cellCount.keys) {
            val neighbours = H3Utils.gridDisk(cell, kCells)
            val score = neighbours.sumOf { cellCount[it] ?: 0 }
            densityScores[cell] = score
        }
        val bestCell = densityScores.maxBy { it.value }.key
        val bestCellCount = densityScores[bestCell]
        val bestCellLatLng = H3Utils.h3ToGeo(bestCell)

        return AccurateMostFrequentedArea(
            Point(bestCellLatLng.first, bestCellLatLng.second),
            bestCellCount ?: 0,
            radius
        )
    }

    fun findMostVisitedArea(waypoints: List<Waypoint>, mostFrequentedAreaRadiusKm: Double): MostFrequentedArea {
        var maxWaypoint: Waypoint? = null
        var maxWaypointEntriesCount: Int = -1

        for (centralWaypoint in waypoints) {
            var cont = 0

            for(waypoint in waypoints){
                val distance = H3Utils.haversineDistance(
                    centralWaypoint.latitude,
                    centralWaypoint.longitude,
                    waypoint.latitude,
                    waypoint.longitude
                )

                if(distance < mostFrequentedAreaRadiusKm){
                    cont++
                }
            }

            if(cont > maxWaypointEntriesCount){
                maxWaypoint = centralWaypoint
                maxWaypointEntriesCount = cont
            }
        }

        return MostFrequentedArea(maxWaypoint,mostFrequentedAreaRadiusKm,maxWaypointEntriesCount)
    }
}