package com.example.elpesoideal_examenpmdmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.elpesoideal_examenpmdmo.Vistas

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login"){
                    Login(navController)
                }
                composable(
                    "pantalla02/{usuario}/{sexo}",
                    arguments = listOf(
                        navArgument("usuario") { type = NavType.StringType },
                        navArgument("sexo") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    Pantalla02(
                        navController = navController,
                        usuario = backStackEntry.arguments?.getString("usuario"),
                        sexo = backStackEntry.arguments?.getString("sexo")
                    )
                }

                composable(
                    "pantalla03/{peso}/{sexo}/{altura}",
                    arguments = listOf(
                        navArgument("peso") { type = NavType.StringType },
                        navArgument("sexo") { type = NavType.StringType },
                        navArgument("altura") { type = NavType.StringType },

                    )
                ) { backStackEntry ->
                    Pantalla03(
                        navController = navController,
                        peso = backStackEntry.arguments?.getString("peso"),
                        sexo = backStackEntry.arguments?.getString("sexo"),
                        alturaSeleccionada = backStackEntry.arguments?.getString("altura")
                    )
                }
            }
        }
    }
}



            @Composable
fun Login(navController: NavController) {
    var textNombre by remember { mutableStateOf("") }
    var textSexo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = textNombre,  // Valor actual del input
            onValueChange = { newText -> textNombre = newText },
            label = { Text("Nombre de usuario") },
            placeholder = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                textSexo = "Hombre"
                navController.navigate("pantalla02/${textNombre}/${textSexo}")

            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )

        ) {
            // Texto del botón
            Text(
                text = "Hombre",
                fontSize = 18.sp, // Tamaño de fuente
                color = Color.White
            )
        }
        Button(
            onClick = {
                textSexo = "Mujer"
                navController.navigate("pantalla02/${textNombre}/${textSexo}")

            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )

        ) {
            // Texto del botón
            Text(
                text = "Mujer",
                fontSize = 18.sp, // Tamaño de fuente
                color = Color.White
            )
        }
    }
}

