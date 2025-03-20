package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.MostFrequentedArea
import it.polito.wa2.g20.routeanalyzer.model.Waypoint

object HotspotAnalyzer {

    fun findMostVisitedArea(waypoints: List<Waypoint>, mostFrequentedAreaRadiusKm: Double): MostFrequentedArea {
        var maxWaypoint: Waypoint? = null
        var maxWaypointEntriesCount: Int = -1

        for (centralWaypoint in waypoints) {
             var cont = 0

            for(waypoint in waypoints){
                val distance = H3Utils.haversineDistance(centralWaypoint.latitude,centralWaypoint.longitude,waypoint.latitude,waypoint.longitude)

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