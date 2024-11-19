package com.istea.parcial2climaapp.presentacion.cities

import CitiesView
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.parcial2climaapp.repository.Repository
import com.istea.parcial2climaapp.router.Enrutador

@Composable
fun CitiesPage(
    navHostController:  NavHostController
) {
    val viewModel : CitiesViewModel = viewModel(
        factory = CitiesViewModelFactory(
            IRepository = Repository(),
            router = Enrutador(navHostController)
        )
    )
    CitiesView(
        state = viewModel.uiState,
        onAction = { intencion ->
            viewModel.execute(intencion)
        }
    )
}
