package com.example.myapplicationtawlaareservationapp.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplicationtawlaareservationapp.data.FirebaseService
import com.example.myapplicationtawlaareservationapp.data.Reservation
import com.example.myapplicationtawlaareservationapp.models.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.util.*

@Composable
fun ReservationScreen(
    navController: NavController,
    restaurantId: Int,
    restaurants: List<Restaurant>
) {
    val context = LocalContext.current
    val firebaseService = FirebaseService()
    val reservationViewModel = remember { ReservationViewModel(firebaseService) }

    val calendar = Calendar.getInstance()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tableNumber by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var peopleCount by remember { mutableStateOf(1) }

    val restaurant = restaurants.find { it.id == restaurantId }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hour, minute ->
            val h = if (hour < 10) "0$hour" else "$hour"
            val m = if (minute < 10) "0$minute" else "$minute"
            selectedTime = "$h:$m"
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {

            Text(
                text = "حجز طاولة في ${restaurant?.name ?: "مطعم"}",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("الاسم الكامل") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("البريد الإلكتروني") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tableNumber,
                onValueChange = { tableNumber = it },
                label = { Text("رقم الطاولة") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                label = { Text("اختر التاريخ") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "اختر التاريخ")
                    }
                }
            )

            OutlinedTextField(
                value = selectedTime,
                onValueChange = {},
                label = { Text("اختر الوقت") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { timePickerDialog.show() }) {
                        Icon(Icons.Default.AccessTime, contentDescription = "اختر الوقت")
                    }
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "عدد الأشخاص:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { if (peopleCount > 1) peopleCount-- },
                    enabled = peopleCount > 1
                ) {
                    Icon(Icons.Default.RemoveCircle, contentDescription = "تقليل")
                }
                Text(text = peopleCount.toString(), style = MaterialTheme.typography.headlineMedium)
                IconButton(onClick = { peopleCount++ }) {
                    Icon(Icons.Default.AddCircle, contentDescription = "زيادة")
                }
            }

            Button(
                onClick = {
                    if (name.isBlank() || email.isBlank() || selectedDate.isBlank() ||
                        selectedTime.isBlank() || tableNumber.isBlank()) {
                        Toast.makeText(context, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // استخدام CoroutineScope مباشرة (مثبت أنه يعمل)
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            Log.d("RESERVATION_DEBUG", "🎯 بدء عملية الحجز...")

                            // 1. أولاً: الانتقال مباشرة إلى شاشة النجاح
                            val success = navigateToSuccessScreen(
                                navController = navController,
                                restaurantName = restaurant?.name ?: "مطعم",
                                name = name,
                                email = email,
                                date = selectedDate,
                                time = selectedTime,
                                tableNumber = tableNumber,
                                peopleCount = peopleCount
                            )

                            if (success) {
                                // 2. ثانياً: حفظ البيانات في Firebase (في الخلفية)
                                val reservation = Reservation(
                                    name = name,
                                    email = email,
                                    date = selectedDate,
                                    time = selectedTime,
                                    peopleCount = peopleCount,
                                    tableNumber = tableNumber,
                                    restaurantName = restaurant?.name ?: "مطعم"
                                )

                                // تشغيل في الخلفية بدون انتظار
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val result = reservationViewModel.addReservation(reservation)
                                        Log.d("RESERVATION_DEBUG", "✅ تم حفظ الحجز في Firebase: $result")
                                    } catch (firebaseError: Exception) {
                                        Log.e("RESERVATION_DEBUG", "⚠️ خطأ في Firebase: ${firebaseError.message}")
                                    }
                                }
                            }

                        } catch (e: Exception) {
                            Log.e("RESERVATION_DEBUG", "❌ خطأ عام: ${e.message}")
                            Toast.makeText(context, "حدث خطأ: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                enabled = name.isNotBlank() && email.isNotBlank() &&
                        selectedDate.isNotBlank() && selectedTime.isNotBlank() &&
                        tableNumber.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("تأكيد الحجز")
            }

            // زر للعودة
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("العودة")
            }

            // زر اختبار مباشر
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        // اختبار مباشر بدون تعقيد
                        navigateToSuccessScreen(
                            navController = navController,
                            restaurantName = "مطعم الاختبار",
                            name = "محمد أحمد",
                            email = "test@test.com",
                            date = "2024-01-01",
                            time = "14:30",
                            tableNumber = "5",
                            peopleCount = 4
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text("اختبار مباشر (بدون حفظ)")
            }
        }
    }
}

// دالة مساعدة للانتقال إلى شاشة النجاح (ليست @Composable)
private fun navigateToSuccessScreen(
    navController: NavController,
    restaurantName: String,
    name: String,
    email: String,
    date: String,
    time: String,
    tableNumber: String,
    peopleCount: Int
): Boolean {
    return try {
        // بناء المسار بشكل صحيح
        val encodedRestaurantName = URLEncoder.encode(restaurantName, "UTF-8")
        val encodedName = URLEncoder.encode(name, "UTF-8")
        val encodedEmail = URLEncoder.encode(email, "UTF-8")

        // تنظيف التاريخ والوقت
        val cleanDate = date.replace("/", "-")
        val cleanTime = time.replace(":", "-")

        val destination =
            "booking_success/" +
                    encodedRestaurantName + "/" +
                    encodedName + "/" +
                    encodedEmail + "/" +
                    cleanDate + "/" +
                    cleanTime + "/" +
                    tableNumber + "/" +
                    peopleCount

        Log.d("NAVIGATION_DEBUG", "🔗 المسار النهائي: $destination")

        // الانتقال بسيط بدون options معقدة
        navController.navigate(destination)

        Log.d("NAVIGATION_DEBUG", "✅ تم الانتقال بنجاح إلى شاشة النجاح")
        true

    } catch (e: Exception) {
        Log.e("NAVIGATION_DEBUG", "❌ خطأ في الانتقال: ${e.message}")

        // محاولة بمسار بسيط كبديل
        try {
            val simpleDestination = "booking_success/test/test/test/test/test/test/1"
            navController.navigate(simpleDestination)
            Log.d("NAVIGATION_DEBUG", "🔄 تم استخدام المسار البسيط كبديل")
            true
        } catch (fallbackError: Exception) {
            Log.e("NAVIGATION_DEBUG", "💥 فشل حتى المسار البسيط: ${fallbackError.message}")
            false
        }
    }
}

// ViewModel مع دالة suspend
class ReservationViewModel(private val firebaseService: FirebaseService) {
    suspend fun addReservation(reservation: Reservation): String {
        return firebaseService.addReservation(reservation)
    }
}