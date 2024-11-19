package com.istea.parcial2climaapp.presentacion.weather.forecast

sealed class ForecastIntention {
    object updateWeather: ForecastIntention()
}
