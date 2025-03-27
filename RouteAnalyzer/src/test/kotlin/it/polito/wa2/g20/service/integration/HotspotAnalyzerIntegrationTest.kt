package it.polito.wa2.g20.service.integration

import io.mockk.clearAllMocks
import it.polito.wa2.g20.model.Waypoint
import it.polito.wa2.g20.service.HotspotAnalyzer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HotspotAnalyzerIntegrationTest {

    @BeforeEach
    fun setup() {
        clearAllMocks()
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
        assertEquals(40.001001152160974, result?.center?.latitude)
        assertEquals(-3.0032907312897934, result?.center?.longitude)
        assertEquals(2, result?.entriesCount)
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
        assertEquals(2, result.entriesCount)
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