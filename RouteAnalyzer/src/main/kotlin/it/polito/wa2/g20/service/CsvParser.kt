package it.polito.wa2.g20.service

import it.polito.wa2.g20.model.Waypoint
import java.io.InputStream

object CsvParser {
    fun parseCsv(inputStream: InputStream): List<Waypoint> {
        val waypoints = mutableListOf<Waypoint>()
        inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val tokens = line.split(";")
                if (tokens.size == 3){
                    val timestamp = tokens[0].toDouble()
                    val latitude = tokens[1].toDouble()
                    val longitude = tokens[2].toDouble()
                    waypoints.add(Waypoint(timestamp, latitude, longitude))
                }
            }
        }
        return waypoints
    }
}