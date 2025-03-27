package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.Waypoint
import it.polito.wa2.g20.routeanalyzer.model.PathDirection
import kotlin.math.*

object DirectionPathCalculator {
    fun computeAverageDirectionPath(waypoints: List<Waypoint>): PathDirection{
        val coordsList = mutableListOf<Pair<Double, Double>>()

        for(i in 0 until waypoints.size - 1){
            val lat1 = waypoints[i].latitude
            val lon1 = waypoints[i].longitude
            val lat2 = waypoints[i + 1].latitude
            val lon2 = waypoints[i + 1].longitude

            val coords = computeBearingCoords(lat1, lon1, lat2, lon2)
            coordsList.add(coords)
        }

        val bearingAvg = computeAverageBearing(coordsList)

        return PathDirection(bearingAvg, bearingToCardinal(bearingAvg))
    }

    fun computeBearingCoords(lat1: Double, lon1: Double, lat2: Double, lon2: Double) : Pair<Double, Double> {
        val lat1Rad = Math.toRadians(lat1)
        val lat2Rad = Math.toRadians(lat2)
        val lon1Rad = Math.toRadians(lon1)
        val lon2Rad = Math.toRadians(lon2)

        val diffLon1 = lon2Rad - lon1Rad

        val y = sin(diffLon1) * cos(lat2Rad)
        val x = cos(lat1Rad) * sin(lat2Rad) - sin(lat1Rad) * cos(lat2Rad) * cos(diffLon1)

        val bearing = atan2(y, x)

        return Pair(cos(bearing), sin(bearing))
    }

    fun bearingToCardinal(bearing: Double): String{
        return when {
            bearing >= 337.5 || bearing < 22.5 -> "North"
            bearing >= 22.5 && bearing < 67.5 -> "North-East"
            bearing >= 67.5 && bearing < 112.5 -> "East"
            bearing >= 112.5 && bearing < 157.5 -> "South-East"
            bearing >= 157.5 && bearing < 202.5 -> "South"
            bearing >= 202.5 && bearing < 247.5 -> "South-West"
            bearing >= 247.5 && bearing < 292.5 -> "West"
            bearing >= 292.5 && bearing < 337.5 -> "North-West"
            else -> "Invalid direction"
        }
    }

    fun computeAverageBearing(coords: List<Pair<Double, Double>>): Double{
        val x = coords.sumOf { it.first}
        val y = coords.sumOf { it.second }

        var avgBearing = Math.toDegrees(atan2(y, x))
        avgBearing = (avgBearing + 360) % 360

        return avgBearing
    }
}