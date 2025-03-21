package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.Waypoint
import it.polito.wa2.g20.routeanalyzer.model.PathDirection
import kotlin.math.*

object DirectionPathCalculator {
    fun computeAverageDirectionPath(waypoints: List<Waypoint>): PathDirection{
        val bearings = mutableListOf<Double>()

        for(i in 0 until waypoints.size - 1){
            val lat1 = waypoints[i].latitude
            val lon1 = waypoints[i].longitude
            val lat2 = waypoints[i + 1].latitude
            val lon2 = waypoints[i + 1].longitude

            val bearing = computeBearing(lat1, lon1, lat2, lon2)
            bearings.add(bearing)
        }

        val bearingAvg = computeAverageBearing(bearings)

        return PathDirection(bearingAvg, bearingToCardinal(bearingAvg))
    }

    fun computeBearing(lat1: Double, lon1: Double, lat2: Double, lon2: Double) : Double{
        val lat1Rad = Math.toRadians(lat1)
        val lat2Rad = Math.toRadians(lat2)
        val lon1Rad = Math.toRadians(lon1)
        val lon2Rad = Math.toRadians(lon2)

        val diffLon1 = lon2Rad - lon1Rad

        val x = sin(diffLon1) * cos(lat2Rad)
        val y = cos(lat1Rad) * sin(lat2Rad) - sin(lat1Rad) * cos(lat2Rad) * cos(diffLon1)
        var bearing = atan2(y, x)

        bearing = (bearing + 360) % 360

        return bearing
    }

    fun bearingToCardinal(bearing: Double): String{
        return when {
            bearing >= 337.5 || bearing < 22.5 -> "Nord"
            bearing >= 22.5 && bearing < 67.5 -> "Nord-Est"
            bearing >= 67.5 && bearing < 112.5 -> "Est"
            bearing >= 112.5 && bearing < 157.5 -> "Sud-Est"
            bearing >= 157.5 && bearing < 202.5 -> "Sud"
            bearing >= 202.5 && bearing < 247.5 -> "Sud-Ovest"
            bearing >= 247.5 && bearing < 292.5 -> "Ovest"
            bearing >= 292.5 && bearing < 337.5 -> "Nord-Ovest"
            else -> "Invalid direction"
        }
    }

    fun computeAverageBearing(bearings: List<Double>): Double{
        val x = bearings.sumOf { cos(Math.toRadians(it)) }
        val y = bearings.sumOf { sin(Math.toRadians(it)) }

        var avgBearing = Math.toDegrees(atan2(y, x))
        avgBearing = (avgBearing + 360) % 360

        return avgBearing
    }
}