package com.example.myapplicationtawlaareservationapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtawlaareservationapp.data.FirebaseService
import com.example.myapplicationtawlaareservationapp.data.Reservation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReservationViewModel(private val firebaseService: FirebaseService) : ViewModel() {

    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations: StateFlow<List<Reservation>> = _reservations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun addReservation(reservation: Reservation) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val reservationId = firebaseService.addReservation(reservation)
                loadReservations() // إعادة تحميل القائمة بعد الإضافة
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add reservation: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadReservations() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                _reservations.value = firebaseService.getAllReservations()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load reservations: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadReservationsByEmail(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                _reservations.value = firebaseService.getReservationsByEmail(email)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load reservations: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteReservation(reservationId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                firebaseService.deleteReservation(reservationId)
                loadReservations() // إعادة تحميل القائمة بعد الحذف
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete reservation: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}