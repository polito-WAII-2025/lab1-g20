package it.polito.wa2.g20

import it.polito.wa2.g20.model.RouteAnalysis
import it.polito.wa2.g20.model.RouteAnalysisAdvanced
import it.polito.wa2.g20.service.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun main() {

    val inputStream1 = CsvParser::class.java.getResourceAsStream("/waypoints.csv")
    val inputStream2 = YmlParser::class.java.getResourceAsStream("/custom-parameters.yml")

    if (inputStream1 == null) {
        println("waypoints.csv not found!")
        return
    }

    if (inputStream2 == null) {
        println("custom-parameters.yaml not found!")
        return
    }

    val waypoints = CsvParser.parseCsv(inputStream1)
    val ymlParams = YmlParser.parseYml(inputStream2, waypoints)
    val geofence = ymlParams.geofence
    val mostFrequentedAreaRadiusKm = ymlParams.mostFrequentedAreaRadiusKm

    println("Loaded ${waypoints.size} waypoints.")

    val maxDistanceFrom = DistanceCalculator.maxDistanceFrom(waypoints[0].latitude, waypoints[0].longitude, waypoints)
    val mostFrequentedArea = HotspotAnalyzer.findMostVisitedArea(waypoints, mostFrequentedAreaRadiusKm)
    val waypointsOutsideGeofence = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)
    val accurateMostFrequentedArea = HotspotAnalyzer.findAccurateMostVisitedArea(waypoints, mostFrequentedAreaRadiusKm)
    val approximateTotalDistance = DistanceCalculator.computeTotalDistance(waypoints)
    val distanceByRootType = RootTypeAnalyzer.distanceForRootType(waypoints)
    val fuelEfficiency = FuelEfficiency(RootTypeSummary(10.0, 7.6923, 6.25), FuelEfficiencyUnit.L_100km)
    val fuelConsumedL = FuelAnalyzer.fuelConsumption(fuelEfficiency, distanceByRootType)
    val averageSpeed = DistanceCalculator.averageSpeed(waypoints)
    val pathDirection = DirectionPathCalculator.computeAverageDirectionPath(waypoints)

    val result = RouteAnalysis(maxDistanceFrom, mostFrequentedArea, waypointsOutsideGeofence)
    val resultAdvanced = RouteAnalysisAdvanced(
        accurateMostFrequentedArea,
        approximateTotalDistance,
        distanceByRootType,
        fuelConsumedL,
        averageSpeed,
        pathDirection
    )


    val outputFile = File("output.json")
    val outputFileAdvanced = File("output_advanced.json")
    val jsonOutput = Json.encodeToString(result)
    val jsonOutputAdvanced = Json.encodeToString(resultAdvanced)
    outputFile.writeText(jsonOutput)
    outputFileAdvanced.writeText(jsonOutputAdvanced)
    println("Output written to output.json")
    println("Output written to output_advanced.json")
}