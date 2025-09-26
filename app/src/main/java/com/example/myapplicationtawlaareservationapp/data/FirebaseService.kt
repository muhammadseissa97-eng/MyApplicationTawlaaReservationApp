package com.example.myapplicationtawlaareservationapp.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.google.firebase.Timestamp

class FirebaseService {
    private val db: FirebaseFirestore = Firebase.firestore

    suspend fun addReservation(reservation: Reservation): String {
        val reservationData = hashMapOf(
            "name" to reservation.name,
            "email" to reservation.email,
            "date" to reservation.date,
            "time" to reservation.time,
            "peopleCount" to reservation.peopleCount,
            "tableNumber" to reservation.tableNumber,
            "restaurantName" to reservation.restaurantName,
            "createdAt" to Timestamp.now()
        )

        val result = db.collection("reservations")
            .add(reservationData)
            .await()

        return result.id
    }

    suspend fun getAllReservations(): List<Reservation> {
        val result = db.collection("reservations")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .await()

        return result.toObjects(Reservation::class.java)
    }

    suspend fun getReservationsByEmail(email: String): List<Reservation> {
        val result = db.collection("reservations")
            .whereEqualTo("email", email)
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .await()

        return result.toObjects(Reservation::class.java)
    }

    suspend fun deleteReservation(reservationId: String) {
        db.collection("reservations")
            .document(reservationId)
            .delete()
            .await()
    }
}