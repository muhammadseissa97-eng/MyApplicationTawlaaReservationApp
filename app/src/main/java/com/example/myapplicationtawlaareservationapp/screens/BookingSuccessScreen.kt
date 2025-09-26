// BookingSuccessScreen.kt
package com.example.myapplicationtawlaareservationapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.net.URLDecoder

@Composable
fun BookingSuccessScreen(
    navController: NavController,
    restaurantName: String,
    name: String,
    email: String,
    date: String,
    time: String,
    tableNumber: String,
    peopleCount: Int
) {

    // فك ترميز القيم
    val decodedRestaurantName = URLDecoder.decode(restaurantName, "UTF-8")
    val decodedName = URLDecoder.decode(name, "UTF-8")
    val decodedEmail = URLDecoder.decode(email, "UTF-8")
    val decodedDate = URLDecoder.decode(date, "UTF-8")
    val decodedTime = URLDecoder.decode(time, "UTF-8")
    val decodedTableNumber = URLDecoder.decode(tableNumber, "UTF-8")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FA)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // رأس الصفحة - نجاح الحجز
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "نجاح",
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )

                    Text(
                        text = "تم الحجز بنجاح! 🎉",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "شكراً لحجزك معنا، سنكون في انتظارك",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // بطاقة تفاصيل الحجز
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "📋 تفاصيل حجزك",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // تفاصيل الحجز
                    DetailRow("🏢 المطعم:", decodedRestaurantName)
                    DetailRow("👤 الاسم:", decodedName)
                    DetailRow("📧 البريد:", decodedEmail)
                    DetailRow("📅 التاريخ:", decodedDate)
                    DetailRow("⏰ الوقت:", decodedTime)
                    DetailRow("🔢 رقم الطاولة:", decodedTableNumber)
                    DetailRow("👥 عدد الأشخاص:", "$peopleCount أشخاص")
                }
            }

            // معلومات مهمة للمستخدم
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "💡 معلومات مهمة",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF57C00)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow("⏰ يرجى الحضور قبل 15 دقيقة من الموعد")
                    InfoRow("📞 للاستفسار: 0123456789")
                    InfoRow("🚫 يمكن الإلغاء قبل ساعتين من الموعد")
                    InfoRow("📧 سيصلك تأكيد على بريدك الإلكتروني")
                }
            }

            // أزرار التنقل
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // زر العودة للرئيسية
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Icon(Icons.Filled.Home, contentDescription = "الرئيسية")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("العودة إلى الرئيسية", fontSize = 16.sp)
                }

                // زر عرض الحجوزات
                Button(
                    onClick = { navController.navigate("view_reservations") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Icon(Icons.Filled.List, contentDescription = "الحجوزات")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("عرض جميع حجوزاتي", fontSize = 16.sp)
                }

                // زر حجز جديد
                OutlinedButton(
                    onClick = {
                        // العودة للخلف ثم فتح شاشة الحجز من جديد
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "حجز جديد")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("حجز طاولة جديدة", fontSize = 16.sp)
                }
            }

            // نص تذييلي
            Text(
                text = "شكراً لاختيارك مطاعمنا - نتمنى لك وجبة شهية! 🍽️",
                color = Color(0xFF666666),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF555555),
            modifier = Modifier.width(120.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun InfoRow(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "•",
            color = Color(0xFFF57C00),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF666666)
        )
    }
}