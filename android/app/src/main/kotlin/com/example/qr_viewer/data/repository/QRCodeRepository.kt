package com.example.qr_viewer.data.repository

import com.example.qr_viewer.data.dao.QRCodeDao
import com.example.qr_viewer.data.model.RoomQRCode
import kotlinx.coroutines.flow.Flow

class QRCodeRepository(private val qrCodeDao: QRCodeDao) {

    // Expose flow of RoomQRCode entities as a stream
    fun getAllQRCodesFlow(): Flow<List<RoomQRCode>> {
        return qrCodeDao.getAllQRCodesFlow()
    }

    // Save a QR code
    suspend fun saveQRCode(roomQrCode: RoomQRCode) {
        qrCodeDao.insertQRCode(roomQrCode)
    }
}