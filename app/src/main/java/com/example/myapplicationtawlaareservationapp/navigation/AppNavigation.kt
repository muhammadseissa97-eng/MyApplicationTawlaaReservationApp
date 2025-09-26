package com.example.myapplicationtawlaareservationapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.myapplicationtawlaareservationapp.models.Restaurant
import com.example.myapplicationtawlaareservationapp.screens.*
import java.net.URLDecoder



data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(NavRoutes.Login, "Login", Icons.Filled.Person),
    BottomNavItem(NavRoutes.Register, "Register", Icons.Filled.Edit),
    BottomNavItem(NavRoutes.Home, "Home", Icons.Filled.Home),
    BottomNavItem(NavRoutes.Reservation, "Reserve", Icons.Filled.DateRange),
    BottomNavItem(NavRoutes.Settings, "Settings", Icons.Filled.Settings),
    BottomNavItem(NavRoutes.About, "About", Icons.Filled.Info)
)

@Composable
fun AppNavigation(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val backgroundColor = when (currentRoute) {
        NavRoutes.Login -> Color(0xFFC7A066)
        NavRoutes.Register -> Color(0xFF89C7C1)
        NavRoutes.Home -> Color(0xFFE8F5E9)
        NavRoutes.Reservation -> Color(0xFFDBDDE3)
        NavRoutes.Settings -> Color(0xFFD0D977)
        NavRoutes.About -> Color(0xFFF3E5F5)
        else -> MaterialTheme.colorScheme.background
    }

    val sampleRestaurants = listOf(
        Restaurant(1, "مطعم النيل الأزرق", "يقدم مأكولات سودانية تقليدية", "الخرطوم - شارع النيل", 15.609, 32.534),
        Restaurant(2, "مطعم السنبلة", "أكلات شرقية وغربية", "بحري - شارع المعرض", 15.640, 32.540),
        Restaurant(3, "مطعم الحوش", "مطعم سوداني بلمسة حديثة", "أم درمان - شارع الأربعين", 15.650, 32.490),
        Restaurant(4, "مطعم سامي", "فطور وعصائر", "الخرطوم - الديوم", 15.590, 32.520),
        Restaurant(5, "مطعم جاردن سيتي", "إطلالة رائعة على النيل", "الخرطوم - جاردن سيتي", 15.610, 32.535),
        Restaurant(6, "مطعم البحراوي", "سمك ومأكولات بحرية", "بحري - شارع المؤسسة", 15.645, 32.545),
        Restaurant(7, "مطعم ود البخيت", "شية وكباب", "أم درمان - سوق أم درمان", 15.655, 32.485)
    )

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            if (currentRoute != NavRoutes.Welcome) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Welcome,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.Welcome) { WelcomeScreen(navController) }
            composable(NavRoutes.Login) { LoginScreen(navController) }
            composable(NavRoutes.Register) { RegisterScreen(navController) }
            composable(NavRoutes.Home) { HomeScreen(navController, sampleRestaurants) }
            composable(NavRoutes.FakeMap) { FakeMapScreen(navController, sampleRestaurants) }

            composable(
                route = NavRoutes.Reservation,
                arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
            ) { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getInt("restaurantId") ?: 0
                ReservationScreen(navController, restaurantId, sampleRestaurants)
            }

            composable(
                route = NavRoutes.BookingSuccess,
                arguments = listOf(
                    navArgument("restaurantName") { type = NavType.StringType },
                    navArgument("name") { type = NavType.StringType },
                    navArgument("email") { type = NavType.StringType },
                    navArgument("date") { type = NavType.StringType },
                    navArgument("time") { type = NavType.StringType },
                    navArgument("tableNumber") { type = NavType.StringType },
                    navArgument("peopleCount") { type = NavType.StringType } // ⬅️ تأكد من StringType
                )
            ) { backStackEntry ->
                val restaurantName = URLDecoder.decode(backStackEntry.arguments?.getString("restaurantName") ?: "", "UTF-8")
                val name = URLDecoder.decode(backStackEntry.arguments?.getString("name") ?: "", "UTF-8")
                val email = URLDecoder.decode(backStackEntry.arguments?.getString("email") ?: "", "UTF-8")
                val date = URLDecoder.decode(backStackEntry.arguments?.getString("date") ?: "", "UTF-8")
                val time = URLDecoder.decode(backStackEntry.arguments?.getString("time") ?: "", "UTF-8")
                val tableNumber = URLDecoder.decode(backStackEntry.arguments?.getString("tableNumber") ?: "", "UTF-8")

                // ⬇️ الحل: استخدام اسم مختلف
                val peopleCountFromArgs = backStackEntry.arguments?.getString("peopleCount") ?: "1"
                val peopleCount = peopleCountFromArgs.toIntOrNull() ?: 1

                BookingSuccessScreen(
                    navController = navController,
                    restaurantName = restaurantName,
                    name = name,
                    email = email,
                    date = date,
                    time = time,
                    tableNumber = tableNumber,
                    peopleCount = peopleCount
                )
            }


            composable(NavRoutes.Settings) { SettingsScreen(navController) }
            composable(NavRoutes.About) { AboutScreen(navController) }
            composable(NavRoutes.ViewReservations) { ViewReservationsScreen(navController) }
            composable(NavRoutes.WebView) { WebScreen(navController, sampleRestaurants) }
            composable(NavRoutes.Restaurants) { RestaurantsListScreen(navController, sampleRestaurants) }

            composable(
                NavRoutes.RestaurantDetail,
                arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
            ) { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getInt("restaurantId") ?: 0
                RestaurantDetailScreen(
                    restaurantId = restaurantId,
                    restaurants = sampleRestaurants,
                    navController = navController
                )
            }
        }
    }
}