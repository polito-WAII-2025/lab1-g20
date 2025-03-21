package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.Geofence
import it.polito.wa2.g20.routeanalyzer.model.Waypoint
import it.polito.wa2.g20.routeanalyzer.model.WaypointsOutsideGeofence

object GeofenceAnalyzer {
    fun countWaypointsOutsideArea(geofence: Geofence, waypoints: List<Waypoint>): WaypointsOutsideGeofence {
        var count = 0
        val outsideWaypoints = mutableListOf<Waypoint>()

        waypoints.forEach {
            val distance = H3Utils.haversineDistance(geofence.latitude, geofence.longitude, it.latitude, it.longitude)

            if(distance > geofence.radius) {
                count++
                outsideWaypoints.add(it)
            }
        }

        return WaypointsOutsideGeofence(Waypoint(0.0, geofence.latitude, geofence.longitude), geofence.radius, count, outsideWaypoints)
    }
}