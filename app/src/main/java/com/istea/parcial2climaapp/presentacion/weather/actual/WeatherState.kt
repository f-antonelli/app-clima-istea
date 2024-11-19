package com.istea.parcial2climaapp.presentacion.weather.actual

sealed class WeatherState {
    data class Exitoso (
        val ciudad: String = "",
        val temperatura: Double = 0.0,
        val descripcion: String= "",
        val st :Double = 0.0,
        ) : WeatherState()
    data class Error(
        val mensaje :String = "",
    ) : WeatherState()
    data object Vacio: WeatherState()
    data object Cargando: WeatherState()

}
