package com.istea.parcial2climaapp.presentacion.weather.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.istea.parcial2climaapp.repository.models.ListForecast


@Composable
fun ForecastView(
    modifier: Modifier = Modifier,
    state: ForecastState,
    onAction: (ForecastIntention) -> Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(ForecastIntention.updateWeather)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is ForecastState.Error -> ErrorView(message = state.mensaje)
            is ForecastState.Exitoso -> ForecastDataView(state.climas)
            ForecastState.Vacio -> LoadingView()
            ForecastState.Cargando -> EmptyView()
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun EmptyView() {
    Text(
        text = "No hay nada que mostrar",
        color = Color.Gray,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun LoadingView() {
    Text(
        text = "Cargando...",
        color = Color(0xFF8A2BE2),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun ErrorView(message: String) {
    Text(
        text = message,
        color = Color(0xFFB00020),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun ForecastDataView(weathers: List<ListForecast>) {
    LazyColumn {
        items(items = weathers) { weather ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "Pronóstico del día",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF8A2BE2)
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = "Temperatura: ${weather.main.temp}°",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF8A2BE2)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Máxima: ${weather.main.temp_max}°",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF3E2723)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Mínima: ${weather.main.temp_min}°",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF3E2723)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Humedad: ${weather.main.humidity}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF3E2723)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastPreviewVacio() {
    ForecastView(state = ForecastState.Vacio, onAction = {})
}

@Preview(showBackground = true)
@Composable
fun ForecastPreviewError() {
    ForecastView(state = ForecastState.Error("Error al cargar. Prueba con otro lugar."), onAction = {})
}


