package com.istea.parcial2climaapp.presentacion.weather.forecast
import com.istea.parcial2climaapp.repository.models.ListForecast

sealed class ForecastState {
    data class Exitoso (
        val climas: List<ListForecast>,
        ) : ForecastState()
    data class Error(
        val mensaje :String = "",
    ) : ForecastState()
    data object Vacio: ForecastState()
    data object Cargando: ForecastState()

}
