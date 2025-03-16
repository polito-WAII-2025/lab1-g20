package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.AccurateMostFrequentedArea
import it.polito.wa2.g20.routeanalyzer.model.Point
import it.polito.wa2.g20.routeanalyzer.model.Waypoint

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
}