package com.example.myapplicationtawlaareservationapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplicationtawlaareservationapp.models.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailScreen(
    restaurantId: Int,
    restaurants: List<Restaurant>,
    navController: NavController
) {
    val restaurant = restaurants.find { it.id == restaurantId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تفاصيل المطعم") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "عودة"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        restaurant?.let { r ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "اسم المطعم: ${r.name}", style = MaterialTheme.typography.headlineSmall)
                Text(text = "العنوان: ${r.address}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "الوصف: ${r.description}", style = MaterialTheme.typography.bodyMedium)

                r.openingHours?.let { Text(text = "ساعات العمل: $it") }
                r.closedDays?.let { Text(text = "أيام الإغلاق: $it") }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        navController.navigate("reservation/${restaurant?.id}")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "احجز الآن")
                }

            }
        } ?: run {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "المطعم غير موجود",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
