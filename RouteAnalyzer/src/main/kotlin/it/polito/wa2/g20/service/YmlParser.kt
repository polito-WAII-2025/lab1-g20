package it.polito.wa2.g20.service

import it.polito.wa2.g20.model.Geofence
import it.polito.wa2.g20.model.Waypoint
import org.yaml.snakeyaml.Yaml
import java.io.InputStream

data class YmlParams(
    val earthRadius: Double,
    val geofence: Geofence,
    val mostFrequentedAreaRadiusKm: Double
)

object YmlParser {
    private val yaml = Yaml()
    private var data: Map<String, Any> = emptyMap()

    fun parseYml(inputStream: InputStream, waypoints: List<Waypoint>): YmlParams {
        data = yaml.load(inputStream)

        val earthRadius = (data["earthRadiusKm"] as Double).toDouble()
        val latitude = (data["geofenceCenterLatitude"] as Double).toDouble()
        val longitude = (data["geofenceCenterLongitude"] as Double).toDouble()
        val radius = (data["geofenceRadiusKm"] as Double).toDouble()

        if(data.containsKey("mostFrequentedAreaRadiusKm")) {
            val mostFrequentedAreaRadiusKm = (data["mostFrequentedAreaRadiusKm"] as Double).toDouble()
            return YmlParams(earthRadius, Geofence(latitude, longitude, radius), mostFrequentedAreaRadiusKm)
        }
        else{
            val maxDistance = DistanceCalculator.maxDistance(waypoints)
            if(maxDistance < 1)
                return YmlParams(earthRadius, Geofence(latitude, longitude, radius), 0.1)
            return YmlParams(earthRadius, Geofence(latitude, longitude, radius), maxDistance / 10.0)
        }
    }
}