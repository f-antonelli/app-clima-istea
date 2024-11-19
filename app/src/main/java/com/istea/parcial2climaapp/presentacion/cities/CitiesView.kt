import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.istea.parcial2climaapp.presentacion.cities.CitiesIntention
import com.istea.parcial2climaapp.presentacion.cities.CitiesState
import com.istea.parcial2climaapp.repository.models.City

@Composable
fun CitiesView(
    modifier: Modifier = Modifier,
    state: CitiesState,
    onAction: (CitiesIntention) -> Unit
) {
    var value by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Text(
            text = "Buscar ciudades",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF8A2BE2),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = value,
            onValueChange = {
                value = it
                onAction(CitiesIntention.Search(value))
            },
            label = { Text("Buscar por nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF3E5F5),
                    shape = MaterialTheme.shapes.medium
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            CitiesState.Cargando -> Text(
                text = "Cargando...",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF8A2BE2)
            )
            is CitiesState.Error -> Text(
                text = state.mensaje,
                color = Color(0xFFB00020),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            is CitiesState.Resultado -> CitiesList(
                ciudades = state.ciudades,
                onSelect = { onAction(CitiesIntention.Select(it)) }
            )
            CitiesState.Vacio -> Text(
                text = "No hay resultados",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CitiesList(ciudades: List<City>, onSelect: (City) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(ciudades) { city ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF8A2BE2).copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(16.dp)
                    .clickable { onSelect(city) },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = city.name,
                    color = Color(0xFF8A2BE2),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
