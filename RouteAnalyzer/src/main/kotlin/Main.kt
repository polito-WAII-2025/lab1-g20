package it.polito.wa2.g20.routeanalyzer
import it.polito.wa2.g20.routeanalyzer.model.RouteAnalysis
import it.polito.wa2.g20.routeanalyzer.service.CsvParser
import it.polito.wa2.g20.routeanalyzer.service.DistanceCalculator
import it.polito.wa2.g20.routeanalyzer.service.GeofenceAnalyzer
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
    val ymlParams = YmlParser.parseYml(inputStream2, waypoints)

    val earthRadius = ymlParams.earthRadius
    val geofence = ymlParams.geofence
    val mostFrequentedAreaRadiusKm = ymlParams.mostFrequentedAreaRadiusKm

    println("Loaded ${waypoints.size} waypoints.")
    println("Geofence: ${geofence.latitude}, ${geofence.longitude}, ${geofence.radius}")

    val maxDistanceFrom = DistanceCalculator.maxDistanceFrom(waypoints[0].latitude, waypoints[0].longitude, waypoints)

    // val mostFrequentedArea = HotspotAnalyzer.findMostVisitedArea(params)
    val waypointsOutsideGeofence = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)
    val result = RouteAnalysis(maxDistanceFrom, waypointsOutsideGeofence)

    val outputFile = File("output.json")
    val jsonOutput = Json.encodeToString(result)
    outputFile.writeText(jsonOutput)
    println("Output written to output.json")
}