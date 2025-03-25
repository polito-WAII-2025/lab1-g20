package it.polito.wa2.g20.routeanalyzer.service

import it.polito.wa2.g20.routeanalyzer.model.EstimatedFuelConsumptionL
import it.polito.wa2.g20.routeanalyzer.model.FuelEfficiency
import it.polito.wa2.g20.routeanalyzer.model.FuelEfficiencyUnit
import it.polito.wa2.g20.routeanalyzer.model.RootTypeSummary
import java.math.BigDecimal
import java.math.RoundingMode

object FuelAnalyzer {


    /**
     * Calculate the fuel consumption of a car for a specific distance, given its fuel efficiency
     * @param fuelEfficiency the fuel efficiency of the car (km/L or L/100km)
     * @param tripDistance the distance travelled by the car for each type of road (km)
     * @return the estimated fuel consumption in Liters
     */
    fun fuelConsumption(fuelEfficiency: FuelEfficiency, tripDistance: RootTypeSummary): EstimatedFuelConsumptionL {
        val convertedEfficiency = convertToKmL(fuelEfficiency)

        val fuelByRootType = RootTypeSummary(
            BigDecimal(tripDistance.highway / convertedEfficiency.roadSpecific.highway)
                .setScale(2, RoundingMode.HALF_EVEN).toDouble(),
            BigDecimal(tripDistance.countyRoad / convertedEfficiency.roadSpecific.countyRoad)
                .setScale(2, RoundingMode.HALF_EVEN).toDouble(),
            BigDecimal(tripDistance.cityRoad / convertedEfficiency.roadSpecific.cityRoad)
                .setScale(2, RoundingMode.HALF_EVEN).toDouble(),
        )

        val totalFuel = fuelByRootType.highway + fuelByRootType.countyRoad + fuelByRootType.cityRoad

        return EstimatedFuelConsumptionL(
            totalFuel,
            fuelByRootType,
        )
    }

    private fun convertToKmL(fuelEfficiency: FuelEfficiency): FuelEfficiency {
        if (fuelEfficiency.unit == FuelEfficiencyUnit.L_100km) {
            return FuelEfficiency(
                RootTypeSummary(
                    100 / fuelEfficiency.roadSpecific.highway,
                    100 / fuelEfficiency.roadSpecific.countyRoad,
                    100 / fuelEfficiency.roadSpecific.cityRoad
                ),
                FuelEfficiencyUnit.km_L
            )
        }
        return fuelEfficiency
    }

}