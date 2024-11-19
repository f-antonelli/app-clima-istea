package com.istea.parcial2climaapp.presentacion.weather.actual

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.istea.parcial2climaapp.ui.theme.Parcial2ClimaAppTheme
import androidx.compose.ui.graphics.Color

@Composable
fun WeatherView(
    modifier: Modifier = Modifier,
    state : WeatherState,
    onAction: (WeatherIntention)->Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(WeatherIntention.updateWeather)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state){
            is WeatherState.Error -> ErrorView(mensaje = state.mensaje)
            is WeatherState.Exitoso -> WeatherDataView(
                ciudad = state.ciudad,
                temperatura = state.temperatura,
                descripcion = state.descripcion,
                st = state.st
            )
            WeatherState.Vacio -> LoadingView()
            WeatherState.Cargando -> EmptyView()
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun EmptyView(){
    Text(
        text = "No hay nada que mostrar",
        color = Color.Gray,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun LoadingView(){
    Text(
        text = "Cargando...",
        color = Color(0xFF8A2BE2),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun ErrorView(mensaje: String){
    Text(
        text = mensaje,
        color = Color(0xFFB00020),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun WeatherDataView(ciudad: String, temperatura: Double, descripcion: String, st: Double){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = ciudad,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF8A2BE2)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${temperatura}°",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF8A2BE2)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3E2723)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Sensación térmica: ${st}°",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3E2723)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreviewVacio() {
    Parcial2ClimaAppTheme {
        WeatherView(state = WeatherState.Vacio, onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreviewError() {
    Parcial2ClimaAppTheme {
        WeatherView(state = WeatherState.Error("Algo salio mal"), onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreviewExitoso() {
    Parcial2ClimaAppTheme {
        WeatherView(state = WeatherState.Exitoso(ciudad = "Mendoza", temperatura = 20.0, descripcion = "Soleado", st = 18.0), onAction = {})
    }
}
