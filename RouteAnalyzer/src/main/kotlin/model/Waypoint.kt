package it.polito.wa2.g20.routeanalyzer.model

import kotlinx.serialization.Serializable

@Serializable
data class Waypoint(
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double
)
