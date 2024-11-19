package com.istea.parcial2climaapp.presentacion.cities

import com.istea.parcial2climaapp.repository.models.City

sealed class CitiesIntention {
    data class Search(val nombre:String ) : CitiesIntention()
    data class Select(val city: City) : CitiesIntention()
}

