package com.istea.parcial2climaapp.repository

import com.istea.parcial2climaapp.repository.models.City
import com.istea.parcial2climaapp.repository.models.MainWeather
import com.istea.parcial2climaapp.repository.models.ListForecast

interface IRepository {
    suspend fun searchCity(city: String): List<City>
    suspend fun getWeather(lat: Float, lon: Float) : MainWeather
    suspend fun getForecast(name: String) : List<ListForecast>
}