package it.polito.wa2.g20.routeanalyzer.model

import kotlinx.serialization.Serializable


@Serializable
data class Point(val latitude: Double, val longitude: Double)

@Serializable
data class AccurateMostFrequentedArea(
    val center: Point,
    val entriesCount: Int,
    val areaRadiusKm: Double,
)

@Serializable
data class PathDirection(
    val degreesDirection: Double,
    val cardinalDirection: String,
)

@Serializable
data class RouteAnalysisAdvanced(
    val accurateMostFrequentedArea: AccurateMostFrequentedArea?,
    val pathDirection: PathDirection,
)