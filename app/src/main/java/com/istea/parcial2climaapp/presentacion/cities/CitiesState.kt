package com.istea.parcial2climaapp.presentacion.cities

import com.istea.parcial2climaapp.repository.models.City

sealed class CitiesState {
    data object Vacio: CitiesState()
    data object Cargando: CitiesState()
    data class Resultado( val ciudades : List<City> ) : CitiesState()
    data class Error(val mensaje: String): CitiesState()
}