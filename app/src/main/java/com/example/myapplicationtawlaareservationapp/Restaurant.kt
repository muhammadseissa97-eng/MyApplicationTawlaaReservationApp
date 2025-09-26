package com.example.myapplicationtawlaareservationapp.models

data class Restaurant(
    val id: Int,
    val name: String,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val openingHours: String? = null,
    val closedDays: String? = null
)
