package com.example.qr_viewer

import android.app.Activity
import android.content.Intent
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.android.FlutterActivity

class MainActivity : FlutterActivity() {

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        val binaryMessenger = flutterEngine.dartExecutor.binaryMessenger

        QRCodeApi.setUp(
            binaryMessenger,
            QRCodeApiImpl(this)
        )

        BiometricAuthApi.setUp(
            binaryMessenger,
            BiometricApiImplementation(this)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == BiometricApiImplementation.REQUEST_CODE_BIOMETRIC) {
            val authResult = data?.getBooleanExtra("auth_result", false) ?: false
            if (resultCode == Activity.RESULT_OK && authResult) {
                // Successful authentication
                println("Biometric Authentication Success!")
            } else {
                // Failed or canceled authentication
                println("Biometric Authentication Failed or Canceled!")
            }
        }
    }
}