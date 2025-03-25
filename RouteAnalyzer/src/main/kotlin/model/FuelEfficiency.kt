package it.polito.wa2.g20.routeanalyzer.model

enum class FuelEfficiencyUnit() {
    km_L,
    L_100km;
}

data class FuelEfficiency(
    val roadSpecific: RootTypeSummary,
    val unit: FuelEfficiencyUnit
)