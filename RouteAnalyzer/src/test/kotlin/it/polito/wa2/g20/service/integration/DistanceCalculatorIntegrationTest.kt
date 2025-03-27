package it.polito.wa2.g20.service.integration

import io.mockk.clearAllMocks
import it.polito.wa2.g20.model.Waypoint
import it.polito.wa2.g20.service.DistanceCalculator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DistanceCalculatorIntegrationTest {

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `maxDistanceFrom should return correct max distance`() {
        val startLat = 40.0
        val startLon = -3.0
        val waypoints = listOf(
            Waypoint(1742057612809, 41.0, -3.1),
            Waypoint(1742057612971, 42.0, -3.2),
            Waypoint(1742057613134, 43.0, -3.3)
        )

        val result = DistanceCalculator.maxDistanceFrom(startLat, startLon, waypoints)

        assertEquals(43.0, result.waypoint.latitude)
        assertEquals(-3.3, result.waypoint.longitude)
        assertEquals(334.51873022839, result.distanceKm)
    }

    // Edge case 1: empty waypoints list
    @Test
    fun `maxDistanceFrom should throw exception when waypoints list is empty`() {
        val startLat = 40.0
        val startLon = -3.0
        val waypoints = emptyList<Waypoint>()

        val exception = assertThrows<IllegalArgumentException> {
            DistanceCalculator.maxDistanceFrom(startLat, startLon, waypoints)
        }

        assertEquals("Waypoints list is empty", exception.message)
    }

    // Edge case 2: only one waypoint
    @Test
    fun `maxDistanceFrom should return correct max distance with only one waypoint`() {
        val startLat = 40.0
        val startLon = -3.0
        val waypoints = listOf(
            Waypoint(1742057612809, 41.0, -3.1)
        )

        val result = DistanceCalculator.maxDistanceFrom(startLat, startLon, waypoints)
        assertEquals(41.0, result.waypoint.latitude)
        assertEquals(-3.1, result.waypoint.longitude)
        assertEquals(111.51603710607161, result.distanceKm)
    }

    @Test
    fun `maxDistance should return the maximum pairwise distance`() {
        val waypoints = listOf(
            Waypoint(1742057612809, 40.0, -3.0),
            Waypoint(1742057612971, 41.0, -3.1),
            Waypoint(1742057613134, 42.0, -3.2)
        )

        val result = DistanceCalculator.maxDistance(waypoints)
        assertEquals(223.02234690280977, result)
    }

    // Edge case 1: empty waypoints list
    @Test
    fun `maxDistance should return 0 when waypoints list is empty`() {
        val waypoints = emptyList<Waypoint>()

        val result = DistanceCalculator.maxDistance(waypoints)

        assertEquals(0.0, result)
    }

    // Edge case 2: only one waypoint
    @Test
    fun `maxDistance should return 0 when waypoints list has only one waypoint`() {
        val waypoints = listOf(
            Waypoint(1742057612809, 40.0, -3.0)
        )

        val result = DistanceCalculator.maxDistance(waypoints)

        assertEquals(0.0, result)
    }
}
