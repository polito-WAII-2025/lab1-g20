package it.polito.wa2.g20.service

import it.polito.wa2.g20.model.MaxDistanceFromStart
import it.polito.wa2.g20.model.Waypoint

object DistanceCalculator {

    fun maxDistanceFrom(startLat: Double, startLon: Double, waypoints: List<Waypoint>): MaxDistanceFromStart {

        val maxWaypoint = waypoints.maxByOrNull { waypoint ->
            H3Utils.haversineDistance(startLat, startLon, waypoint.latitude, waypoint.longitude)
        } ?: throw IllegalArgumentException("Waypoints list is empty")

        val maxDistanceKm = H3Utils.haversineDistance(startLat, startLon, maxWaypoint.latitude, maxWaypoint.longitude)

        return MaxDistanceFromStart(maxWaypoint, maxDistanceKm)
    }

    fun maxDistance(waypoints: List<Waypoint>): Double {
        var maxDistance = 0.0

        for (waypoint in waypoints) {
            for (otherWaypoint in waypoints) {
                val distance = H3Utils.haversineDistance(
                    waypoint.latitude,
                    waypoint.longitude,
                    otherWaypoint.latitude,
                    otherWaypoint.longitude
                )
                if (distance > maxDistance) {
                    maxDistance = distance
                }
            }
        }

        return maxDistance
    }


}