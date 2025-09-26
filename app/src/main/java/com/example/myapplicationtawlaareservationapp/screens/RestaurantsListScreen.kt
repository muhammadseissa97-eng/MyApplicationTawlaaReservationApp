package com.example.myapplicationtawlaareservationapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplicationtawlaareservationapp.models.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsListScreen(navController: NavController, restaurants: List<Restaurant>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("قائمة المطاعم") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "رجوع"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(restaurants) { restaurant ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("restaurant_detail/${restaurant.id}")
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = restaurant.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = restaurant.address, style = MaterialTheme.typography.bodyMedium)
                        Button(
                            onClick = {
                                navController.navigate("reservation/${restaurant?.id}")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "احجز الآن")
                        }

                    }
                }
            }
        }
    }
}
