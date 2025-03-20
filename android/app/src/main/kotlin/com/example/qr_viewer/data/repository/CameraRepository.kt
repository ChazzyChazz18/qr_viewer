package com.example.qr_viewer.data.repository

import android.util.Log
import com.example.qr_viewer.data.dao.QRCodeDao
import com.example.qr_viewer.data.model.QRCode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CameraRepository(
    private val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(),
    private val qrCodeDao: QRCodeDao
) {
    suspend fun scanQRCode(inputImage: InputImage): String? {
        return try {
            suspendCoroutine { continuation ->
                barcodeScanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->
                        val result = barcodes.firstOrNull()?.rawValue
                        continuation.resume(result)
                    }
                    .addOnFailureListener { e ->
                        Log.e("CameraRepository", "Error scanning QR code: ${e.message}")
                        continuation.resume(null)
                    }
            }
        } catch (e: Exception) {
            Log.e("CameraRepository", "Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun saveQRCode(qrCode: QRCode) {
        withContext(Dispatchers.IO) {
            qrCodeDao.insertQRCode(qrCode)
        }
    }
}
