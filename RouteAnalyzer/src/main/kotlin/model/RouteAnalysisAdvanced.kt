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
data class RootTypeSummary(
    val highway: Double,
    val countyRoad: Double,
    val cityRoad: Double
)

@Serializable
data class EstimatedFuelConsumptionL(
    val total: Double,
    val roadSpecific: RootTypeSummary,
)


@Serializable
data class RouteAnalysisAdvanced(
    val accurateMostFrequentedArea: AccurateMostFrequentedArea?,
    val approximateTotalDistanceKm: Double,
    val rootTypeSummaryKm: RootTypeSummary,
    val estimatedFuelConsumptionL: EstimatedFuelConsumptionL
)