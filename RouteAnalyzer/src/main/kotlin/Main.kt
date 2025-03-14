package it.polito.wa2.g20.routeanalyzer
import it.polito.wa2.g20.routeanalyzer.model.RouteAnalysis
import it.polito.wa2.g20.routeanalyzer.service.CsvParser
import it.polito.wa2.g20.routeanalyzer.service.DistanceCalculator
import it.polito.wa2.g20.routeanalyzer.service.YmlParser
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun main() {

    val inputStream1 = CsvParser::class.java.getResourceAsStream("/waypoints.csv")
    val inputStream2 = YmlParser::class.java.getResourceAsStream("/custom-parameters.yml")

    if (inputStream1 == null) {
        println("Fi1le not found!")
        return
    }

    if (inputStream2 == null) {
        println("File not found!")
        return
    }

    val waypoints = CsvParser.parseCsv(inputStream1)
    val geofence = YmlParser.parseYml(inputStream2)

    println("Loaded ${waypoints.size} waypoints.")
    println("Geofence: ${geofence.latitude}, ${geofence.longitude}, ${geofence.radius}")

    val maxDistanceFrom = DistanceCalculator.maxDistanceFrom(waypoints[0].latitude, waypoints[0].longitude, waypoints)

    val earthRadius = YmlParser.parseYmlparams(maxDistanceFrom.distanceKm).first
    val maxDistanceFromStart = YmlParser.parseYmlparams(maxDistanceFrom.distanceKm).second
    // val mostFrequentedArea = HotspotAnalyzer.findMostVisitedArea(params)
    // val waypointsOutsideGeofence = GeofenceAnalyzer.countWaypointsOutsideArea(params)
    val result = RouteAnalysis(maxDistanceFrom)

    val outputFile = File("output.json")
    val jsonOutput = Json.encodeToString(result)
    outputFile.writeText(jsonOutput)
    println("Output written to output.json")
}