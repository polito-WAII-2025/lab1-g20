package it.polito.wa2.g20.routeanalyzer.model

import kotlinx.serialization.Serializable

@Serializable
data class MaxDistanceFromStart(
    val waypoint: Waypoint,
    val distanceKm: Double,
)

@Serializable
data class Point(val latitude: Double, val longitude: Double)

@Serializable
data class AccurateMostFrequentedArea(
    val center: Point,
    val entriesCount: Int,
    val areaRadiusKm: Double,
)

@Serializable
data class WaypointsOutsideGeofence(
    val centralWaypoint: Waypoint,
    val areaRadiusKm: Double,
    val count: Int,
    val waypoints: List<Waypoint>
)

// data class MostFrequentedArea()

@Serializable
data class RouteAnalysis(
    val maxDistanceFromStart: MaxDistanceFromStart,
    // val mostFrequentedArea: MostFrequentedArea,
    val waypointsOutsideGeofence: WaypointsOutsideGeofence,
    val mostFrequentedArea: AccurateMostFrequentedArea?,
)
