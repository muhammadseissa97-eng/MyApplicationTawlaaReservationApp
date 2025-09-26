package com.example.myapplicationtawlaareservationapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplicationtawlaareservationapp.data.FirebaseService
import com.example.myapplicationtawlaareservationapp.data.Reservation
import com.example.myapplicationtawlaareservationapp.viewmodel.ReservationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewReservationsScreen(navController: NavController) {
    val context = LocalContext.current
    val firebaseService = FirebaseService()
    val reservationViewModel = remember { ReservationViewModel(firebaseService) }

    var reservations by remember { mutableStateOf<List<Reservation>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // تحميل الحجوزات من Firebase عند فتح الشاشة
    LaunchedEffect(Unit) {
        try {
            reservationViewModel.loadReservations()
        } catch (e: Exception) {
            println("Error loading reservations: ${e.message}")
        }
    }

    // مراقبة التغييرات في ViewModel
    LaunchedEffect(reservationViewModel.reservations) {
        reservations = reservationViewModel.reservations.value
        isLoading = reservationViewModel.isLoading.value
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Reservations") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (reservations.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No reservations found.", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                } else {
                    items(reservations) { reservation ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Name: ${reservation.name}", style = MaterialTheme.typography.titleMedium)
                                Text("Email: ${reservation.email}", style = MaterialTheme.typography.bodyMedium)
                                Text("Restaurant: ${reservation.restaurantName}", style = MaterialTheme.typography.bodyMedium)
                                Text("Date: ${reservation.date}", style = MaterialTheme.typography.bodyMedium)
                                Text("Time: ${reservation.time}", style = MaterialTheme.typography.bodyMedium)
                                Text("People: ${reservation.peopleCount}", style = MaterialTheme.typography.bodyMedium)
                                Text("Table: ${reservation.tableNumber}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}