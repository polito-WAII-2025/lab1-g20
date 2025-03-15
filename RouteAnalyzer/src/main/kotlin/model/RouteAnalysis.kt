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
data class AccurateMostFrequentedAreaPoint(
    val point: Point,
    val entriesCount: Int,
    val areaRadiusKm: Double,
)

// data class MostFrequentedArea()
// data class WaypointsOutsideGeofence()

@Serializable
data class RouteAnalysis(
    val maxDistanceFromStart: MaxDistanceFromStart,
    val mostFrequentedAreaPoint: AccurateMostFrequentedAreaPoint?,
    // val mostFrequentedArea: MostFrequentedArea,
    // val waypointsOutsideGeofence: WaypointsOutsideGeofence
)
