package com.example.qr_viewer

import android.content.Intent
import com.example.qr_viewer.ui.scanner.CameraActivity
import io.flutter.embedding.android.FlutterActivity

class QRCodeApiImpl(private val activity: FlutterActivity) : QRCodeApi {
    override fun scanQRCode(): String {
        val intent = Intent(activity, CameraActivity::class.java)
        activity.startActivity(intent)
        
        return "Sample QR Code Data"
    }

    override fun getAllSavedQRCodes(callback: (Result<List<QRCode?>>) -> Unit) {
        val qrCodes = listOf(QRCode("Code1"), QRCode("Code2"))
        val result = Result.success(qrCodes)
        callback(result)
    }
}
