package com.example.myapplicationtawlaareservationapp.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Reservation(
    @DocumentId val id: String = "",
    val name: String = "",
    val email: String = "",
    val date: String = "",
    val time: String = "",
    val peopleCount: Int = 1,
    val tableNumber: String = "",
    val restaurantName: String = "",
    val createdAt: Timestamp = Timestamp.now()
)