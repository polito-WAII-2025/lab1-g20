package it.polito.wa2.g20.service

import com.uber.h3core.AreaUnit
import com.uber.h3core.H3Core
import com.uber.h3core.LengthUnit
import com.uber.h3core.util.LatLng
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.sqrt

const val TARGET_HEX_COUNT = 7.0

object H3Utils {
    private val h3 = H3Core.newInstance()

    /**
     * Returns the distance between two points on the Earth's surface using the haversine formula
     * @param lat1 the latitude of the first point
     * @param lon1 the longitude of the first point
     * @param lat2 the latitude of the second point
     * @param lon2 the longitude of the second point
     * @return the distance between two points on the Earth's surface using the haversine formula
     */
    fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val point1 = LatLng(lat1, lon1)
        val point2 = LatLng(lat2, lon2)
        return  h3.greatCircleDistance(point1, point2, LengthUnit.km)
    }

    /**
     * Returns the resolution that best approximates the area of a circle with the given radius and TARGET_HEX_COUNT
     * ```
     * n_hexagons = (π * r²) / A_hex
     * ```
     * @param radius the radius of the circle
     * @return the resolution that best approximates the area of a circle with the given radius and TARGET_HEX_COUNT
     */
    fun getResolution(radius: Double): Int {
        val resMap = (0..15).associateWith { h3.getHexagonAreaAvg(it, AreaUnit.m2) }
        return resMap.minBy { abs((Math.PI * radius * radius) / it.value - TARGET_HEX_COUNT) }.key
    }

    /**
     * Returns the number of rings of hexagons needed to cover a circle with the given radius
     * ```
     * n_rings = (radius - r_hex) / (2 * r_hex)
     * ```
     * @param radius the radius of the circle
     * @param resolution the resolution of the hexagons
     * @return the number of rings of hexagons needed to cover a circle with the given radius
     */
    fun countRings(radius: Double, resolution: Int): Int {
        val area = h3.getHexagonAreaAvg(resolution, AreaUnit.m2)
        val radiusHex = sqrt(area / Math.PI)
        return round((radius - radiusHex) / (2 * radiusHex)).toInt()
    }

    /**
     * Returns the list of h3 cells which are at most grid distance k from the origin cell.
     * @param center the origin cell
     * @param k the maximum distance from the origin cell
     * @return the list of h3 cells which are at most grid distance k from the origin cell
     */
    fun gridDisk(center: Long, k: Int): List<Long> {
        return h3.gridDisk(center, k)
    }

    /**
     * Returns the h3 cell containing the given coordinates at the given resolution
     * @param lat the latitude of the coordinates
     * @param lon the longitude of the coordinates
     * @param resolution the resolution of the cell
     * @return the h3 cell containing the given coordinates at the given resolution
     */
    fun geoToH3(lat: Double, lon: Double, resolution: Int): Long {
        return h3.latLngToCell(lat, lon, resolution)
    }

    /**
     * Returns the coordinates of the center of the given h3 cell
     * @param h3Index the h3 cell
     * @return the coordinates of the center of the given h3 cell
     */
    fun h3ToGeo(h3Index: Long): Pair<Double, Double> {
        val coordinates = h3.cellToLatLng(h3Index)
        return Pair(coordinates.lat, coordinates.lng)
    }

}