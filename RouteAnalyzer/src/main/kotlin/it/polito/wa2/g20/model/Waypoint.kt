package it.polito.wa2.g20.model

import kotlinx.serialization.Serializable

@Serializable
data class Waypoint(
    val timestamp: Double,
    val latitude: Double,
    val longitude: Double
)
