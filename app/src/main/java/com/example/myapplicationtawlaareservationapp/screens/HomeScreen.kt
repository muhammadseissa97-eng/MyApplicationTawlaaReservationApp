package com.example.myapplicationtawlaareservationapp.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationtawlaareservationapp.R
import com.example.myapplicationtawlaareservationapp.models.Restaurant
import com.example.myapplicationtawlaareservationapp.ui.theme.TableReservationAppTheme

@Composable
fun HomeScreen(
    navController: NavController,
    restaurants: List<Restaurant> = emptyList()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var showLogoutDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.map_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.25f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = "مرحباً بك في الصفحة الرئيسية",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (restaurants.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        items(restaurants) { restaurant ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(restaurant.name, style = MaterialTheme.typography.titleMedium)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(restaurant.description)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Button(
                                            onClick = { navController.navigate("restaurant_detail/${restaurant.id}") },
                                            modifier = Modifier.weight(1f)
                                        ) { Text("تفاصيل") }
                                        Button(
                                            onClick = { navController.navigate("reservation/${restaurant.id}") },
                                            modifier = Modifier.weight(1f)
                                        ) { Text("احجز الآن") }

                                    }
                                }
                            }
                        }
                    }
                } else {
                    Text("لا توجد مطاعم متاحة حالياً")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("fake_map") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("عرض الخريطة")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout",
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("تسجيل الخروج", fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    activity?.finishAffinity()
                }) { Text("نعم") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("لا") }
            },
            title = { Text("تأكيد الخروج") },
            text = { Text("هل أنت متأكد أنك تريد الخروج من التطبيق؟") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleRestaurants = listOf(
        Restaurant(1, "مطعم النيل الأزرق", "يقدم مأكولات سودانية تقليدية", "الخرطوم - شارع النيل", 15.609, 32.534),
        Restaurant(2, "مطعم السنبلة", "أكلات شرقية وغربية", "بحري - شارع المعرض", 15.640, 32.540),
        Restaurant(3, "مطعم الحوش", "مطعم سوداني بلمسة حديثة", "أم درمان - شارع الأربعين", 15.650, 32.490),
    )
    TableReservationAppTheme {
        HomeScreen(rememberNavController(), sampleRestaurants)
    }
}
