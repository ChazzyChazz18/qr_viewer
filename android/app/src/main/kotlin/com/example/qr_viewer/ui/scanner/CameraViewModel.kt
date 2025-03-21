package com.example.qr_viewer.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.mlkit.vision.common.InputImage
import com.example.qr_viewer.data.repository.CameraRepository
import android.util.Log

class CameraViewModel(private val repository: CameraRepository) : ViewModel() {
    private val _detectedQRCode = MutableLiveData<String?>()
    val detectedQRCode: LiveData<String?> = _detectedQRCode

        fun processImage(inputImage: InputImage) {
        viewModelScope.launch {
            try {
                val qrCode = repository.scanQRCode(inputImage)
                _detectedQRCode.postValue(qrCode)
            } catch (e: Exception) {
                Log.e("CameraViewModel", "Error processing image: ${e.message}")
            }
        }
    }

    fun onUrlHandled() {
        _detectedQRCode.postValue(null)
    }
}
