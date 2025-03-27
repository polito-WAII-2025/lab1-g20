package it.polito.wa2.g20.service.unit

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import it.polito.wa2.g20.model.Geofence
import it.polito.wa2.g20.model.Waypoint
import it.polito.wa2.g20.service.GeofenceAnalyzer
import it.polito.wa2.g20.service.H3Utils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GeofenceAnalyzerTest {

    @BeforeEach
    fun setup() {
        clearAllMocks()
        unmockkAll()
        // mock the H3Utils object
        mockkObject(H3Utils)
        every { H3Utils.haversineDistance(any(), any(), any(), any()) } answers {
            val lat1 = arg<Double>(0)
            val lon1 = arg<Double>(1)
            val lat2 = arg<Double>(2)
            val lon2 = arg<Double>(3)
            (lat2 - lat1) + (lon2 - lon1)
        }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun `countWaypointsOutsideArea should return 0 when all waypoints are inside`() {
        val geofence = Geofence(40.0, -3.0, 2.0)
        val waypoints = listOf(
            Waypoint(1742057612809, 40.5, -2.5),
            Waypoint(1742057612971, 40.2, -2.8)
        )

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(0, result.count)
        assertEquals(emptyList<Waypoint>(), result.waypoints)
    }

    @Test
    fun `countWaypointsOutsideArea should count waypoints outside`() {
        val geofence = Geofence(40.0, -3.0, 2.0)
        val waypoints = listOf(
            Waypoint(1742057612809, 43.0, -3.0),
            Waypoint(1742057612971, 40.5, -2.5),
            Waypoint(1742057613134, 42.0, -3.5)
        )

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(1, result.count)
        assertEquals(listOf(waypoints[0]), result.waypoints)
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
        val geofence = Geofence(40.0, -3.0, 2.0)
        val waypoints = listOf(
            Waypoint(1742057612809, 40.5, -2.5)
        )

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(0, result.count)
        assertEquals(emptyList<Waypoint>(), result.waypoints)
    }

    @Test
    fun `countWaypointsOutsideArea should work with one waypoint outside`() {
        val geofence = Geofence(40.0, -3.0, 2.0)
        val waypoints = listOf(
            Waypoint(1742057612809, 43.0, -3.0)
        )

        val result = GeofenceAnalyzer.countWaypointsOutsideArea(geofence, waypoints)

        assertEquals(1, result.count)
        assertEquals(listOf(waypoints[0]), result.waypoints)
    }

}