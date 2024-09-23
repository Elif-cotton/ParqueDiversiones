package com.example.parque2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.parque2.ui.theme.ParkEntryTheme
import java.util.Locale
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkEntryTheme {
                ParkEntryApp()
            }
        }
    }
}

@Composable
fun ParkEntryApp() {
    var age by remember { mutableStateOf(TextFieldValue("")) }
    var dayOfWeek by remember { mutableStateOf(TextFieldValue("")) }
    var price by remember { mutableStateOf("") }

    // Lista de días de la semana en español
    val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Ingrese su edad", fontSize = 20.sp) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = dayOfWeek,
            onValueChange = { dayOfWeek = it },
            label = { Text("Ingrese el día de la semana", fontSize = 20.sp) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                price = calculateTicketPrice(age.text, dayOfWeek.text, daysOfWeek)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio", fontSize = 20.sp,   fontWeight = FontWeight.Bold,)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = price)
    }
}

fun calculateTicketPrice(ageText: String, dayOfWeek: String, daysOfWeek: List<String>): String {
    val age = ageText.toIntOrNull() ?: return "Por favor, ingrese una edad válida."
    if (age < 0 || age > 100) return "Edad no válida."

    // Convertir la primera letra del día a mayúscula y verificar que está en la lista
    val day = dayOfWeek.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    if (day !in daysOfWeek) return "Día de la semana no válido."

    return when {
        age < 4 -> "Entrada gratuita."
        age <= 15 -> "El precio de la entrada es 15.000 CLP."
        age in 16..60 -> {
            if (day == "Lunes" || day == "Martes") {
                "El precio de la entrada es 25.000 CLP."
            } else {
                "El precio de la entrada es 30.000 CLP."
            }
        }
        age > 60 -> "El precio de la entrada es 20.000 CLP."
        else -> "Error en la entrada."
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ParkEntryTheme {
        ParkEntryApp()
    }
}