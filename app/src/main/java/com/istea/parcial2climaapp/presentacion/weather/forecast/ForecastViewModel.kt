package com.istea.parcial2climaapp.presentacion.weather.forecast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.parcial2climaapp.repository.IRepository
import com.istea.parcial2climaapp.router.Router
import kotlinx.coroutines.launch

class ForecastViewModel(
    val repository: IRepository,
    val router: Router,
    val name: String
) : ViewModel() {

    var uiState by mutableStateOf<ForecastState>(ForecastState.Vacio)

    fun execute(intencion: ForecastIntention){
        when(intencion){
            ForecastIntention.updateWeather -> getForecast()
        }
    }

    fun getForecast() {
        uiState = ForecastState.Cargando
        viewModelScope.launch {
            try{
                val forecast = repository.getForecast(name).filter {
                    true
                }
                uiState = ForecastState.Exitoso(forecast)
            } catch (exception: Exception){
                uiState = ForecastState.Error(exception.localizedMessage ?: "error desconocido")
            }
        }
    }

}

class ForecastViewModelFactory(
    private val IRepository: IRepository,
    private val router: Router,
    private val nombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            return ForecastViewModel(IRepository,router,nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}