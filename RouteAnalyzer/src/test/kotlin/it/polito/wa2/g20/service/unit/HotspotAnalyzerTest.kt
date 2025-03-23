package it.polito.wa2.g20.service.unit

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import it.polito.wa2.g20.model.Waypoint
import it.polito.wa2.g20.service.H3Utils
import it.polito.wa2.g20.service.HotspotAnalyzer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HotspotAnalyzerTest {

    @BeforeEach
    fun setup() {
        clearAllMocks()
        unmockkAll()
        // mock the H3Utils object
        mockkObject(H3Utils)

        // Mock the H3Utils methods

        every { H3Utils.getResolution(any()) } returns 5

        every { H3Utils.geoToH3(any(), any(), any()) } answers {
            val lat = firstArg<Double>()
            lat.toLong()
        }

        every { H3Utils.countRings(any(), any()) } returns 2

        every { H3Utils.gridDisk(any(), any()) } answers {
            listOf(firstArg<Long>(), firstArg<Long>() + 1, firstArg<Long>() + 2)
        }

        every { H3Utils.h3ToGeo(any()) } answers {
            Pair(firstArg<Long>().toDouble(), firstArg<Long>().toDouble() + 0.5)
        }

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
    fun `findAccurateMostVisitedArea should return the most frequented area`() {
        val waypoints = listOf(
            Waypoint(1742057612809.0, 40.0, -3.0),
            Waypoint(1742057612971.0, 40.0, -3.0),
            Waypoint(1742057613134.0, 41.0, -3.1)
        )
        val radius = 1.0 // 1 km

        val result = HotspotAnalyzer.findAccurateMostVisitedArea(waypoints, radius)

        assertNotNull(result)
        assertEquals(40.0, result?.center?.latitude)
        assertEquals(40.5, result?.center?.longitude)
        assertEquals(3, result?.entriesCount)
    }

    @Test
    fun `findAccurateMostVisitedArea should return null when no waypoints are given`() {
        val waypoints = emptyList<Waypoint>()
        val radius = 1.0

        val result = HotspotAnalyzer.findAccurateMostVisitedArea(waypoints, radius)

        assertNull(result)
    }


    @Test
    fun `findMostVisitedArea should return the waypoint with most nearby points`() {
        val waypoints = listOf(
            Waypoint(1742057612809.0, 40.0, -3.0),
            Waypoint(1742057612971.0, 40.0, -3.0),
            Waypoint(1742057613134.0, 41.0, -3.1)
        )
        val radius = 1.0 // 1 km

        val result = HotspotAnalyzer.findMostVisitedArea(waypoints, radius)

        assertNotNull(result)
        assertEquals(40.0, result.centralWaypoint?.latitude)
        assertEquals(-3.0, result.centralWaypoint?.longitude)
        assertEquals(3, result.entriesCount)
    }

    @Test
    fun `findMostVisitedArea should return empty result when no waypoints are given`() {
        val waypoints = emptyList<Waypoint>()
        val radius = 1.0

        val result = HotspotAnalyzer.findMostVisitedArea(waypoints, radius)

        assertNull(result.centralWaypoint)
        assertEquals(0, result.entriesCount)
    }

}