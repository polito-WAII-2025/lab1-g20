package it.polito.wa2.g20.service.integration

import io.mockk.clearAllMocks
import it.polito.wa2.g20.model.Geofence
import it.polito.wa2.g20.model.Waypoint
import it.polito.wa2.g20.service.GeofenceAnalyzer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GeofenceAnalyzerIntegrationTest {

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `countWaypointsOutsideArea should return 0 when all waypoints are inside`() {
        val geofence = Geofence(40.0, -3.0, 100.0)
        val waypoints = listOf(
            Waypoint(1742057612809.0, 40.5, -2.5),
            Waypoint(1742057612971.0, 40.2, -2.8)
        )

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(0, result.count)
        assertEquals(emptyList<Waypoint>(), result.waypoints)
    }

    @Test
    fun `countWaypointsOutsideArea should count waypoints outside`() {
        val geofence = Geofence(40.0, -3.0, 2.0)
        val waypoints = listOf(
            Waypoint(1742057612809.0, 43.0, -3.0),
            Waypoint(1742057612971.0, 40.5, -2.5),
            Waypoint(1742057613134.0, 42.0, -3.5)
        )

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(3, result.count)
        assertEquals(listOf(waypoints[0], waypoints[1], waypoints[2]), result.waypoints)
    }

    @Test
    fun `countWaypointsOutsideArea should return 0 when no waypoints are provided`() {
        val geofence = Geofence(40.0, -3.0, 2.0)
        val waypoints = emptyList<Waypoint>()

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(0, result.count)
        assertEquals(emptyList<Waypoint>(), result.waypoints)
    }

    @Test
    fun `countWaypointsOutsideArea should work with one waypoint inside`() {
        val geofence = Geofence(40.0, -3.0, 100.0)
        val waypoints = listOf(
            Waypoint(1742057612809.0, 40.5, -2.5)
        )

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(0, result.count)
        assertEquals(emptyList<Waypoint>(), result.waypoints)
    }

    @Test
    fun `countWaypointsOutsideArea should work with one waypoint outside`() {
        val geofence = Geofence(40.0, -3.0, 2.0)
        val waypoints = listOf(
            Waypoint(1742057612809.0, 43.0, -3.0)
        )

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(1, result.count)
        assertEquals(listOf(waypoints[0]), result.waypoints)
    }

}