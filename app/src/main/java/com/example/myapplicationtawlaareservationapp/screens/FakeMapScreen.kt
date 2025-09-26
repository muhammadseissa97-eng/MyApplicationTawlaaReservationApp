package com.example.myapplicationtawlaareservationapp.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplicationtawlaareservationapp.R
import com.example.myapplicationtawlaareservationapp.models.Restaurant
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun FakeMapScreen(navController: NavController, restaurants: List<Restaurant>) {
    // المطعم الذي تم الضغط عليه
    var selectedRestaurantId by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 🖼️ صورة الخريطة
        Image(
            painter = painterResource(id = R.drawable.map_background),
            contentDescription = "خريطة وهمية",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 📍 الماركرات
        restaurants.forEachIndexed { index, restaurant ->
            // نحسب مكان كل مطعم (موزع بشكل متساوي)
            val xFraction = (index + 1).toFloat() / (restaurants.size + 1)
            val yFraction = 0.5f + (index % 2) * 0.1f // بعض المطاعم أعلى وأسفل قليلاً

            // هل هذا هو المطعم المختار؟
            val isSelected = selectedRestaurantId == restaurant.id

            // حركة التكبير
            val scale by animateFloatAsState(if (isSelected) 1.5f else 1f)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(
                        start = (xFraction * 300).dp, // تقريبياً حسب حجم الصورة
                        top = (yFraction * 500).dp
                    )
                    .clickable {
                        selectedRestaurantId = restaurant.id
                        // عند الضغط، يفتح صفحة تفاصيل المطعم
                        navController.navigate("restaurant_detail/${restaurant.id}")
                    }
            ) {
                // أيقونة الموقع مع ظل
                Image(
                    painter = painterResource(id = R.drawable.baseline_location_on_24),

                            contentDescription = restaurant.name,
                    modifier = Modifier
                        .size((40 * scale).dp)
                        .shadow(elevation = 6.dp, shape = MaterialTheme.shapes.medium),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FakeMapScreenPreview() {
    val navController = rememberNavController()
    val sampleRestaurants = listOf(
        Restaurant(1, "مطعم النيل الأزرق", "يقدم مأكولات سودانية تقليدية", "الخرطوم - شارع النيل", 15.609, 32.534),
        Restaurant(2, "مطعم السنبلة", "أكلات شرقية وغربية", "بحري - شارع المعرض", 15.640, 32.540),
        Restaurant(3, "مطعم الحوش", "مطعم سوداني بلمسة حديثة", "أم درمان - شارع الأربعين", 15.650, 32.490),
        Restaurant(4, "مطعم سامي", "فطور وعصائر", "الخرطوم - الديوم", 15.590, 32.520)
    )

    FakeMapScreen(navController, sampleRestaurants)
}
