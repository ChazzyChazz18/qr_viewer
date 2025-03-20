package com.example.qr_viewer

import com.example.qr_viewer.QRCodeApi
import com.example.qr_viewer.QRCodeApiImpl
import io.flutter.plugin.common.MethodChannel
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.android.FlutterActivity

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.qr_viewer/camera"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        QRCodeApi.setUp(flutterEngine.dartExecutor.binaryMessenger, QRCodeApiImpl(this))

        // MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
        //     if (call.method == "startCamera") {
        //         // Lógica para iniciar CameraX
        //         startCamera()
        //         result.success("Camera started")
        //     } else {
        //         result.notImplemented()
        //     }
        // }
    }

    // private fun startCamera() {
    //     // Inicia la actividad de CameraX
    //     val intent = Intent(this, CameraActivity::class.java)
    //     startActivity(intent)
    // }
}