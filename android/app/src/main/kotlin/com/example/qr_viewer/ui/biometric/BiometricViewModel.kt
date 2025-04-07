package com.example.qr_viewer.ui.biometric

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BiometricViewModel : ViewModel() {
    private val _authResultFlow = MutableSharedFlow<Result<Boolean>>(extraBufferCapacity = 1)

    fun emitAuthResult(result: Result<Boolean>) {
        viewModelScope.launch {
            _authResultFlow.emit(result) // Emit the authentication result
        }
    }

    fun collectAuthResult(callback: (Result<Boolean>) -> Unit) {
        viewModelScope.launch {
            try {
                val result = _authResultFlow.first() // Wait for the first emitted result
                callback(result)
            } catch (e: Exception) {
                callback(Result.failure(e))
            }
        }
    }
}