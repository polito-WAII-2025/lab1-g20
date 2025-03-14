package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.Geofence
import it.polito.wa2.g20.routeanalyzer.model.MaxDistanceFromStart
import org.yaml.snakeyaml.Yaml
import java.io.InputStream

object YmlParser {
    private val yaml = Yaml()
    private var data: Map<String, Any> = emptyMap()

    fun parseYml(inputStream: InputStream): Geofence {
        data = yaml.load<Map<String, Any>>(inputStream)

        val latitude = (data["geofenceCenterLatitude"] as Double).toDouble()
        val longitude = (data["geofenceCenterLongitude"] as Double).toDouble()
        val radius = (data["geofenceRadiusKm"] as Double).toDouble()

        return Geofence(latitude, longitude, radius)
    }

    fun parseYmlparams(maxDistanceFromStart: Double):  Pair<Double, Double> {
        val earthRadius = (data["earthRadiusKm"] as Double).toDouble()

        if(data.containsKey("mostFrequentedAreaRadiusKm")) {
            val mostFrequentedAreaRadiusKm = (data["mostFrequentedAreaRadiusKm"] as Double).toDouble()
            return Pair(earthRadius, mostFrequentedAreaRadiusKm)
        }

        return Pair(earthRadius, maxDistanceFromStart / 10.0)
    }
}