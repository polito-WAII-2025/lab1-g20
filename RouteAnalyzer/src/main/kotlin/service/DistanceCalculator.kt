package it.polito.wa2.g20.routeanalyzer.service
import it.polito.wa2.g20.routeanalyzer.model.MaxDistanceFromStart
import it.polito.wa2.g20.routeanalyzer.model.Waypoint

object DistanceCalculator {

    fun maxDistanceFrom(startLat: Double, startLon: Double, waypoints: List<Waypoint>): MaxDistanceFromStart {

        val maxWaypoint = waypoints.maxByOrNull { waypoint ->
            H3Utils.haversineDistance(startLat, startLon, waypoint.latitude, waypoint.longitude)
        } ?: throw IllegalArgumentException("Waypoints list is empty")

        val maxDistanceKm = H3Utils.haversineDistance(startLat, startLon, maxWaypoint.latitude, maxWaypoint.longitude)

        return MaxDistanceFromStart(maxWaypoint, maxDistanceKm)

    }
}