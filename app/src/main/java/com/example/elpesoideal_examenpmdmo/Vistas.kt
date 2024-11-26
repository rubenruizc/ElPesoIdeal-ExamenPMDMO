package com.example.elpesoideal_examenpmdmo

import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.times
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.os.persistableBundleOf
import androidx.navigation.NavController
import kotlin.time.times

class Vistas : ComponentActivity() {

}
@Composable
fun ItemList(ListaAlturas: List<Alturas>) {
        LazyColumn {
            items(ListaAlturas) { itemAltura ->
                Text(text = "Altura: ${itemAltura.valor} m", fontSize = 16.sp)
            }
        }
    }
@Composable
fun Pantalla02(navController: NavController, usuario: String?, sexo: String?) {
    val alturas = listOf(
        Alturas(1.50), Alturas(1.55), Alturas(1.60), Alturas(1.65),
        Alturas(1.70), Alturas(1.75), Alturas(1.80), Alturas(1.85)
    )

    var pesoVisible by remember { mutableStateOf(false) }
    var peso by remember { mutableStateOf("") }
    var alturaSeleccionada by remember { mutableStateOf<Alturas?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nombre: ${usuario ?: "Nobody"}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Sexo: ${sexo ?: "Nulo"}")
        Spacer(modifier = Modifier.height(10.dp))

        // Si no se ha seleccionado altura, muestra la lista de alturas
        if (!pesoVisible) {
            Text(text = "Seleccione su altura:")

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(alturas) { altura ->
                    Button(
                        onClick = {
                            alturaSeleccionada = altura
                            pesoVisible = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text(text = "${altura.valor} m")
                    }
                }
            }
        } else {
            // Si ya se seleccionó altura, muestra el campo para ingresar el peso
            Text(text = "Ingrese su peso (kg):")

            TextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso") },
                placeholder = { Text("Peso en kg") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Botón para navegar a Pantalla03 con los datos
            Button(
                onClick = {
                    // Si el peso es válido, navega a la siguiente pantalla

                    if (peso.isNotEmpty() && alturaSeleccionada != null) {
                        // Al navegar, pasamos el peso como String y la altura seleccionada como String
                        navController.navigate("pantalla03/${peso}/${sexo ?: ""}/${alturaSeleccionada?.valor}")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "Calcular IMC")
            }
        }
    }
}





@Composable
fun Pantalla03(navController: NavController, peso: String?, sexo: String?, alturaSeleccionada: String?) {
    // Verifica que la altura y el peso no sean nulos y conviértelos a valores Double
    val altura: Double = alturaSeleccionada?.toDoubleOrNull() ?: 1.0 // Si es nulo o no puede convertirse, usa 1.0
    val pesoDouble: Double = peso?.toDoubleOrNull() ?: 0.0

    // Calcula el coeficiente dependiendo del sexo
    val coeficiente: Double = if (sexo == "Hombre") 1.0 else 0.95

    // Realiza el cálculo del IMC
    val imc = (pesoDouble * coeficiente) / (altura * altura)

    // Calcula la clasificación del IMC
    val respuesta = when {
        imc < 18.5 -> "Bajo peso: IMC < 18.5"
        imc in 18.5..24.9 -> "Peso normal: IMC entre 18.5 y 24.9"
        imc in 25.0..29.9 -> "Sobrepeso: IMC entre 25 y 29.9"
        else -> "Obesidad: IMC >= 30"
    }

    // Interfaz de usuario
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Altura: ${altura} m")
        Text(text = "Peso: ${peso} kg")
        Text(text = "IMC: %.2f".format(imc))
        Text(text = respuesta)
    }
}

