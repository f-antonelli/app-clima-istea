package com.istea.parcial2climaapp.repository

import com.istea.parcial2climaapp.repository.models.City
import com.istea.parcial2climaapp.repository.models.ForecastDTO
import com.istea.parcial2climaapp.repository.models.ListForecast
import com.istea.parcial2climaapp.repository.models.MainWeather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Repository : IRepository {

    private val apiKey = "95e93e4f7a36fc511148468d1774792d"

    private val client = HttpClient(){
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun searchCity(city: String): List<City> {
        val res = client.get("https://api.openweathermap.org/geo/1.0/direct"){
            parameter("q", city)
            parameter("limit",100)
            parameter("appid",apiKey)
        }

        if (res.status == HttpStatusCode.OK){
            val cities = res.body<List<City>>()
            return cities
        }else{
            throw Exception()
        }
    }

    override suspend fun getWeather(lat: Float, lon: Float): MainWeather {
        val res = client.get("https://api.openweathermap.org/data/2.5/weather"){
            parameter("lat",lat)
            parameter("lon",lon)
            parameter("units","metric")
            parameter("appid",apiKey)
        }
        if (res.status == HttpStatusCode.OK){
            val weather = res.body<MainWeather>()
            return weather
        }else{
            throw Exception()
        }
    }

    override suspend fun getForecast(name: String): List<ListForecast> {

        val res = client.get("https://api.openweathermap.org/data/2.5/forecast"){
            parameter("q", name)
            parameter("units","metric")
            parameter("appid",apiKey)
        }
        if (res.status == HttpStatusCode.OK){
            val forecast = res.body<ForecastDTO>()
            return forecast.list
        }else{
            throw Exception()
        }

    }
}