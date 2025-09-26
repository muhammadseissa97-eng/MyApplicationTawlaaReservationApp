package com.example.myapplicationtawlaareservationapp.screens

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AboutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "حول تطبيق الحجز",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = """
                هذا التطبيق هو نظام لحجز الطاولات في المطاعم، تم تطويره باستخدام Android Studio و Jetpack Compose.
                
                📌 الميزات الرئيسية:
                • تسجيل دخول وتسجيل مستخدم جديد  
                • استعراض قائمة المطاعم وتفاصيل كل مطعم  
                • حجز طاولة بتاريخ ووقت وعدد أشخاص  
                • عرض الحجوزات السابقة  
                • خريطة لعرض موقع المطاعم (WebView)  
                • مزامنة البيانات مع Firebase Firestore  

                🚀 تم تطوير هذا التطبيق باستخدام:
                • Android Studio  
                • Jetpack Compose  
                • Kotlin  
                • Firebase Authentication  
                • Firebase Firestore (NoSQL)  
                • Navigation Compose  
                • Material Design 3  

                📞 اتصل بنا:
                • 0966844146  
                • 0969295845  
                • 0902524670
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text("العودة إلى الصفحة الرئيسية")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(navController = rememberNavController())
}