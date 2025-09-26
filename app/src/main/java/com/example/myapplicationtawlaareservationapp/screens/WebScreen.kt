package com.example.myapplicationtawlaareservationapp.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.myapplicationtawlaareservationapp.models.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(navController: NavController, restaurants: List<Restaurant>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("الخريطة") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                    }
                }
            )
        }
    ) { paddingValues ->
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true

                    // تحويل بيانات المطاعم إلى JSON وإرسالها إلى صفحة HTML
                    val json = restaurants.joinToString(
                        prefix = "[", postfix = "]"
                    ) { """{"lat":${it.latitude},"lon":${it.longitude},"name":"${it.name}"}""" }

                    // تمرير البيانات عبر URL
                    loadUrl("file:///android_asset/osm_map.html?restaurants=$json")
                }
            },
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}
