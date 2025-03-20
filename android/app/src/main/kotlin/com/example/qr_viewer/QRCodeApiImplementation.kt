package com.example.qr_viewer

import android.content.Intent
import io.flutter.embedding.android.FlutterActivity

class QRCodeApiImpl(private val activity: FlutterActivity) : QRCodeApi {
    override fun scanQRCode(): QRCodeData {
        val intent = Intent(activity, CameraActivity::class.java)
        activity.startActivity(intent)

        // Replace this with your real QR code processing logic
        return QRCodeData("Sample QR Code Data")
    }
}
