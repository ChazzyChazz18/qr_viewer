package com.example.qr_viewer

import android.content.Intent
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.android.FlutterActivity

class MainActivity : FlutterActivity() {

    private lateinit var biometricApi: BiometricApiImplementation

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        val binaryMessenger = flutterEngine.dartExecutor.binaryMessenger

        QRCodeApi.setUp(
            binaryMessenger,
            QRCodeApiImplementation(this)
        )

        biometricApi = BiometricApiImplementation(this)
        BiometricAuthApi.setUp(
            binaryMessenger,
            biometricApi
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BiometricApiImplementation.REQUEST_CODE_BIOMETRIC) {
            biometricApi.handleBiometricResult(resultCode, data)
        }
    }
}