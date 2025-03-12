package it.polito.wa2.g20.routeanalyzer
import it.polito.wa2.g20.routeanalyzer.model.RouteAnalysis
import it.polito.wa2.g20.routeanalyzer.service.CsvParser
import it.polito.wa2.g20.routeanalyzer.service.DistanceCalculator
import it.polito.wa2.g20.routeanalyzer.service.HotspotAnalyzer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun main() {

    val inputStream = CsvParser::class.java.getResourceAsStream("/waypoints.csv")

    if (inputStream == null) {
        println("File not found!")
        return
    }
    val waypoints = CsvParser.parseCsv(inputStream)
    println("Loaded ${waypoints.size} waypoints.")

    val maxDistanceFrom = DistanceCalculator.maxDistanceFrom(waypoints[0].latitude, waypoints[0].longitude, waypoints)
    // val mostFrequentedArea = HotspotAnalyzer.findMostVisitedArea(params)
    // val waypointsOutsideGeofence = GeofenceAnalyzer.countWaypointsOutsideArea(params)
    val result = RouteAnalysis(maxDistanceFrom)

    val outputFile = File("output.json")
    val jsonOutput = Json.encodeToString(result)
    outputFile.writeText(jsonOutput)
    println("Output written to output.json")
}