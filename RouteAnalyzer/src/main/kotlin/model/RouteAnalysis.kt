package it.polito.wa2.g20.routeanalyzer.model

import kotlinx.serialization.Serializable

@Serializable
data class MaxDistanceFromStart(
    val waypoint: Waypoint,
    val distanceKm: Double,
)

@Serializable
data class MostFrequentedArea(
    val centralWaypoint: Waypoint?,
    val areaRadiusKm: Double,
    val entriesCount: Int
)

@Serializable
data class RouteAnalysis(
    val maxDistanceFromStart: MaxDistanceFromStart,
    val mostFrequentedArea: MostFrequentedArea
)
