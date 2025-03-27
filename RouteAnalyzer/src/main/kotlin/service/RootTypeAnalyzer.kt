package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.RootTypeSummary
import it.polito.wa2.g20.routeanalyzer.model.Waypoint

object RootTypeAnalyzer {

    fun distanceForRootType(waypoints: List<Waypoint>): RootTypeSummary {
        var highwayDistance: Double = 0.0
        var countyRoadDistance: Double = 0.0
        var cityRoadDistance: Double = 0.0

        for (index in 0 until waypoints.size - 1) {
            val waypoint1 = waypoints[index]
            val waypoint2 = waypoints[index + 1]

            val distance = H3Utils.haversineDistance(
                waypoint1.latitude,
                waypoint1.longitude,
                waypoint2.latitude,
                waypoint2.longitude
            ) * 1000

            val time = (waypoint2.timestamp - waypoint1.timestamp) / 1000
            val mediumSpeed = distance / time * 3.6

            if (mediumSpeed <= 50) {
                cityRoadDistance += distance/1000
            } else if (mediumSpeed <= 90) {
                countyRoadDistance += distance/1000
            } else {
                highwayDistance += distance/1000
            }
        }

        return RootTypeSummary(highwayDistance, countyRoadDistance, cityRoadDistance)
    }
}