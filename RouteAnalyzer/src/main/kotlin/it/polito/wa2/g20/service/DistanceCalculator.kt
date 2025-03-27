package it.polito.wa2.g20.service
import it.polito.wa2.g20.model.MaxDistanceFromStart
import it.polito.wa2.g20.model.Waypoint
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.max

object DistanceCalculator {

    fun maxDistanceFrom(startLat: Double, startLon: Double, waypoints: List<Waypoint>): MaxDistanceFromStart {

        val maxWaypoint = waypoints.maxByOrNull { waypoint ->
            H3Utils.haversineDistance(startLat, startLon, waypoint.latitude, waypoint.longitude)
        } ?: throw IllegalArgumentException("Waypoints list is empty")

        val maxDistanceKm = H3Utils.haversineDistance(startLat, startLon, maxWaypoint.latitude, maxWaypoint.longitude)

        return MaxDistanceFromStart(maxWaypoint, maxDistanceKm)
    }

    fun maxDistance(waypoints: List<Waypoint>): Double {
        var maxDistance = 0.0

        for (waypoint in waypoints) {
            for (otherWaypoint in waypoints) {
                val distance = H3Utils.haversineDistance(waypoint.latitude, waypoint.longitude, otherWaypoint.latitude, otherWaypoint.longitude)
                if (distance > maxDistance) {
                    maxDistance = distance
                }
            }
        }

        return maxDistance
    }

    private fun computeTotalHaversineDistance(waypoints: List<Waypoint>): Double {
        var totalDistance = 0.0

        for (i in 0 until waypoints.size - 1) {
            totalDistance += H3Utils.haversineDistance(
                waypoints[i].latitude,
                waypoints[i].longitude,
                waypoints[i + 1].latitude,
                waypoints[i + 1].longitude
            )
        }

        return totalDistance
    }

    fun computeTotalDistance(waypoints: List<Waypoint>): Double {
        var maxDistance = 0.0
        val totalDistance = computeTotalHaversineDistance(waypoints)
        for (i in 0 until waypoints.size - 1) {
            maxDistance = BigDecimal(
                max(
                    H3Utils.haversineDistance(
                        waypoints[i].latitude, waypoints[i].longitude,
                        waypoints[i + 1].latitude, waypoints[i + 1].longitude
                    ),
                    maxDistance
                )
            ).setScale(3, RoundingMode.HALF_EVEN).toDouble()
        }
        val avgDistance = (totalDistance / waypoints.size + maxDistance) / 2
        return avgDistance * waypoints.size
    }

    fun averageSpeed(waypoints: List<Waypoint>): Double {
        if (waypoints.size < 2) {
            throw IllegalArgumentException("Waypoints list is empty")
        }
        val totalDistance = computeTotalDistance(waypoints)
        val totalDuration = (waypoints.last().timestamp - waypoints.first().timestamp) / 3600000
        return BigDecimal(totalDistance / totalDuration).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}