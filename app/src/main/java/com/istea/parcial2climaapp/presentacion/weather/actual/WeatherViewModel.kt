package com.istea.parcial2climaapp.presentacion.weather.actual

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.parcial2climaapp.repository.IRepository
import com.istea.parcial2climaapp.router.Router
import kotlinx.coroutines.launch

class WeatherViewModel(
    val repository: IRepository,
    val router: Router,
    val lat : Float,
    val lon : Float,
    val name: String
) : ViewModel() {

    var uiState by mutableStateOf<WeatherState>(WeatherState.Vacio)

    fun execute(intencion: WeatherIntention){
        when(intencion){
            WeatherIntention.updateWeather -> traerClima()
        }
    }

    fun traerClima() {
        uiState = WeatherState.Cargando
        viewModelScope.launch {
            try{
                val weather = repository.getWeather(lat = lat, lon = lon)
                uiState = WeatherState.Exitoso(
                    ciudad = weather.name ,
                    temperatura = weather.main.temp,
                    descripcion = weather.weather.first().description,
                    st = weather.main.feels_like,
                )
            } catch (exception: Exception){
                uiState = WeatherState.Error(exception.localizedMessage ?: "error desconocido")
            }
        }
    }

}

class WeatherViewModelFactory(
    private val IRepository: IRepository,
    private val router: Router,
    private val lat: Float,
    private val lon: Float,
    private val nombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(IRepository,router,lat,lon,nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}