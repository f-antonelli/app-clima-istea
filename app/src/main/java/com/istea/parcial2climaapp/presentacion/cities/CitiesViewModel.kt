package com.istea.parcial2climaapp.presentacion.cities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.parcial2climaapp.repository.IRepository
import com.istea.parcial2climaapp.repository.models.City
import com.istea.parcial2climaapp.router.Router
import com.istea.parcial2climaapp.router.Ruta
import kotlinx.coroutines.launch

class CitiesViewModel(
    val IRepository: IRepository,
    val router: Router
) : ViewModel(){

    var uiState by mutableStateOf<CitiesState>(CitiesState.Vacio)
    var cities : List<City> = emptyList()

    fun execute(intention: CitiesIntention){
        when(intention){
            is CitiesIntention.Search -> search(nombre = intention.nombre)
            is CitiesIntention.Select -> select(city = intention.city)
        }
    }

    private fun search(nombre: String){

        uiState = CitiesState.Cargando
        viewModelScope.launch {
            try {
                cities = IRepository.searchCity(nombre)
                if (cities.isEmpty()) {
                    uiState = CitiesState.Vacio
                } else {
                    uiState = CitiesState.Resultado(cities)
                }
            } catch (exeption: Exception){
                uiState = CitiesState.Error(exeption.message ?: "error desconocido")
            }
        }
    }

    private fun select(city: City){
        val ruta = Ruta.Clima(
            lat = city.lat,
            lon = city.lon,
            nombre = city.name
        )
        router.navegar(ruta)
    }
}


class CitiesViewModelFactory(
    private val IRepository: IRepository,
    private val router: Router
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitiesViewModel::class.java)) {
            return CitiesViewModel(IRepository,router) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}