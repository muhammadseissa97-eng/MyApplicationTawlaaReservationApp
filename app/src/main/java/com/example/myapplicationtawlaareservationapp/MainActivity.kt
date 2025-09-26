package com.example.myapplicationtawlaareservationapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationtawlaareservationapp.navigation.AppNavigation
import com.example.myapplicationtawlaareservationapp.ui.theme.TableReservationAppTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ تهيئة Firebase أولاً
        initializeFirebase()

        enableEdgeToEdge()
        setContent {
            TableReservationAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavigation(navController = navController)
                }
            }
        }
    }

    private fun initializeFirebase() {
        try {
            Log.d("Firebase", "🔥 بدء تهيئة Firebase...")

            // ✅ الطريقة الآمنة لتهيئة Firebase
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this)
                Log.d("Firebase", "✅ تم تهيئة Firebase بنجاح")
            } else {
                Log.d("Firebase", "⚠️ Firebase مهيأ مسبقاً")
            }

            // ✅ اختبار الاتصال بطريقة بديلة
            testFirebaseConnection()

        } catch (e: Exception) {
            Log.e("Firebase", "❌ خطأ في تهيئة Firebase: ${e.message}")
        }
    }

    private fun testFirebaseConnection() {
        try {
            // ✅ الطريقة الصحيحة - استخدم FirebaseFirestore مباشرة
            val db = FirebaseFirestore.getInstance()

            val testData = hashMapOf(
                "timestamp" to System.currentTimeMillis(),
                "device" to "Android",
                "app_name" to "Tawlaa Reservation",
                "status" to "connection_test",
                "package" to "com.example.myapplicationtawlaareservationapp"
            )

            db.collection("connection_tests")
                .document("test_${System.currentTimeMillis()}")
                .set(testData)
                .addOnSuccessListener {
                    Log.d("Firebase", "🎉 نجح الاتصال بـ Firestore!")
                    Log.d("Firebase", "✅ تم حفظ البيانات بنجاح")
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "❌ فشل الاتصال: ${e.message}")

                    // رسائل توضيحية
                    when {
                        e.message?.contains("PERMISSION_DENIED") == true -> {
                            Log.e("Firebase", "🔒 تحتاج تعديل قواعد Firestore")
                        }
                        e.message?.contains("UNAVAILABLE") == true -> {
                            Log.e("Firebase", "📡 تحقق من اتصال الإنترنت")
                        }
                        e.message?.contains("failed to connect") == true -> {
                            Log.e("Firebase", "🌐 مشكلة في الاتصال بالخادم")
                        }
                        else -> {
                            Log.e("Firebase", "⚠️ خطأ غير معروف: ${e.localizedMessage}")
                        }
                    }
                }

        } catch (e: Exception) {
            Log.e("Firebase", "💥 خطأ غير متوقع: ${e.message}")
            e.printStackTrace()
        }
    }
}