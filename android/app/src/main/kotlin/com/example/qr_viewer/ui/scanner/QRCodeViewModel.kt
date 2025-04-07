package com.example.qr_viewer.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qr_viewer.data.model.RoomQRCode
import com.example.qr_viewer.utils.toPigeonQRCode
import com.example.qr_viewer.data.repository.QRCodeRepository
import com.example.qr_viewer.pigeon.QRCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class QRCodeViewModel @Inject constructor(
    private val repository: QRCodeRepository
) : ViewModel() {

    private val _qrCodes = MutableStateFlow<List<QRCode>>(emptyList())
    val qrCodes: StateFlow<List<QRCode>> get() = _qrCodes

    init { loadAllQRCodes() }

    private fun loadAllQRCodes() {
        viewModelScope.launch {
            repository.getAllQRCodesFlow()
                .map { roomQRCodes ->
                    roomQRCodes.map { it.toPigeonQRCode() }
                }
                .collect { mappedQRCodes ->
                    _qrCodes.emit(mappedQRCodes)
                }
        }
    }

    fun saveQRCode(qrCode: RoomQRCode) {
        viewModelScope.launch {
            repository.saveQRCode(qrCode)
        }
    }
}