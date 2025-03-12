package it.polito.wa2.g20.routeanalyzer.service
import com.uber.h3core.H3Core
import com.uber.h3core.LengthUnit
import com.uber.h3core.util.LatLng

object H3Utils {
    private val h3 = H3Core.newInstance()

    fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val point1 = LatLng(lat1, lon1)
        val point2 = LatLng(lat2, lon2)
        return  h3.greatCircleDistance(point1, point2, LengthUnit.km)
    }

}