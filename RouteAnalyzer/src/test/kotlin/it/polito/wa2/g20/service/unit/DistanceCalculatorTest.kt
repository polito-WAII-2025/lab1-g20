package it.polito.wa2.g20.service.unit

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import it.polito.wa2.g20.model.Waypoint
import it.polito.wa2.g20.service.DistanceCalculator
import it.polito.wa2.g20.service.H3Utils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DistanceCalculatorTest {

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
        assertEquals(2.7, result.distanceKm)
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

        every { H3Utils.haversineDistance(startLat, startLon, any(), any()) } answers {
            val lat = thirdArg<Double>()
            val lon = arg<Double>(3)
            (lat - startLat) + (lon - startLon)
        }

        val result = DistanceCalculator.maxDistanceFrom(startLat, startLon, waypoints)
        assertEquals(41.0, result.waypoint.latitude)
        assertEquals(-3.1, result.waypoint.longitude)
        assertEquals(0.8999999999999999, result.distanceKm)
    }

    @Test
    fun `maxDistance should return the maximum pairwise distance`() {
        val waypoints = listOf(
            Waypoint(1742057612809, 40.0, -3.0),
            Waypoint(1742057612971, 41.0, -3.1),
            Waypoint(1742057613134, 42.0, -3.2)
        )

        val result = DistanceCalculator.maxDistance(waypoints)
        assertEquals(1.7999999999999998, result)
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
