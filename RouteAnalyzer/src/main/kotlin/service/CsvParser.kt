package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.Waypoint
import java.io.InputStream
import kotlin.math.floor

object CsvParser {
    fun parseCsv(inputStream: InputStream): List<Waypoint> {
        val waypoints = mutableListOf<Waypoint>()
        inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val tokens = line.split(";")
                if (tokens.size == 3){
                    val timestamp = floor(tokens[0].toDouble()).toLong()
                    val latitude = tokens[1].toDouble()
                    val longitude = tokens[2].toDouble()
                    waypoints.add(Waypoint(timestamp, latitude, longitude))
                }
            }
        }
        return waypoints
    }
}