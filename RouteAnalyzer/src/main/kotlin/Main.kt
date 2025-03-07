package it.polito.wa2.g20
import com.uber.h3core.H3Core

fun example(): Long {
    val h3 = H3Core.newInstance()
    val lat = 37.7955
    val lng = -122.3937
    val res = 10
    return h3.latLngToCell(lat, lng, res)
}//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    println("Cell: " + example())
}