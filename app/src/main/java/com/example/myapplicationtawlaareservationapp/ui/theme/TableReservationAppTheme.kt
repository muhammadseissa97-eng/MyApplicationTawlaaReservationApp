package com.example.myapplicationtawlaareservationapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationtawlaareservationapp.screens.RegisterScreen

private val LightColors = lightColorScheme(
    primary = Color(0xFF009688),
    onPrimary = Color.Blue,
    background = Color(0xFF355B60),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

@Composable
fun TableReservationAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    TableReservationAppTheme {
        RegisterScreen(navController = rememberNavController())
    }
}
