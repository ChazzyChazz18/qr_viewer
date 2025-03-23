package com.example.qr_viewer

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.example.qr_viewer.data.database.QRCodeDatabase
import com.example.qr_viewer.ui.scanner.CameraActivity
import io.flutter.embedding.android.FlutterActivity
import kotlinx.coroutines.launch
import com.example.qr_viewer.data.model.toPigeonQRCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.qr_viewer.data.model.QRCode

class QRCodeApiImplementation(private val activity: FlutterActivity?) : QRCodeApi {

    companion object {
        const val SCAN_QR_REQUEST_CODE = 1002 // Define a unique integer code for QR scanning
    }

    override fun scanQRCode(): String {
        val intent = Intent(activity, CameraActivity::class.java)
        activity?.startActivityForResult(intent, SCAN_QR_REQUEST_CODE)

        return "Sample QR Code Data"
    }

    suspend fun saveQRCode(qrCode: QRCode, activity: CameraActivity) {
        val qrCodeDao = QRCodeDatabase.getDatabase(activity).qrCodeDao()
        withContext(Dispatchers.IO) {
            qrCodeDao.insertQRCode(qrCode)
        }
    }


    override fun getAllSavedQRCodes(callback: (Result<List<com.example.qr_viewer.QRCode?>>) -> Unit) {
        val qrCodeDao = QRCodeDatabase.getDatabase(activity!!).qrCodeDao()

        activity.lifecycleScope.launch {
            try {
                val qrCodesFromRoom = qrCodeDao.getAllQRCodes()

                // Handle nullability for qrCodesFromRoom.value
                val mappedQRCodes = qrCodesFromRoom.value?.map { it.toPigeonQRCode() } ?: emptyList()

                // Ensure mappedQRCodes satisfies the expected type
                callback(Result.success(mappedQRCodes))
            } catch (e: Exception) {
                callback(Result.failure(e))
            }
        }
    }
}
