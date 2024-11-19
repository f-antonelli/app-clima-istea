package com.istea.parcial2climaapp.presentacion.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.parcial2climaapp.presentacion.weather.actual.WeatherView
import com.istea.parcial2climaapp.presentacion.weather.actual.WeatherViewModel
import com.istea.parcial2climaapp.presentacion.weather.actual.WeatherViewModelFactory
import com.istea.parcial2climaapp.presentacion.weather.forecast.ForecastView
import com.istea.parcial2climaapp.presentacion.weather.forecast.ForecastViewModel
import com.istea.parcial2climaapp.presentacion.weather.forecast.ForecastViewModelFactory
import com.istea.parcial2climaapp.repository.Repository
import com.istea.parcial2climaapp.router.Enrutador

@Composable
fun WeatherPage(
    navHostController: NavHostController,
    lat : Float,
    lon : Float,
    nombre: String
){
    val viewModel : WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(
            IRepository = Repository(),
            router = Enrutador(navHostController),
            lat = lat,
            lon = lon,
            nombre = nombre
        )
    )
    val forecastViewModel : ForecastViewModel = viewModel(
        factory = ForecastViewModelFactory(
            IRepository = Repository(),
            router = Enrutador(navHostController),
            nombre = nombre
        )
    )

    Column {
        WeatherView(
            state = viewModel.uiState,
            onAction = { intencion ->
                viewModel.execute(intencion)
            }
        )
        ForecastView(
            state = forecastViewModel.uiState,
            onAction = { intencion ->
                forecastViewModel.execute(intencion)
            }
        )
    }

}
