package com.example.qr_viewer

import android.content.Intent
import android.os.Bundle
import com.example.qr_viewer.ui.ActivityLauncher
import com.example.qr_viewer.ui.scanner.QRCodeViewModel
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity(), ActivityLauncher {

    private lateinit var qrCodeViewModel: QRCodeViewModel

    private lateinit var biometricApi: BiometricApiImplementation

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Initialize dependencies
        DependencyProvider.initialize(this.applicationContext)

        qrCodeViewModel = DependencyProvider.provideQRCodeViewModel()

        val binaryMessenger = flutterEngine.dartExecutor.binaryMessenger

        QRCodeApi.setUp(
            binaryMessenger,
            QRCodeApiImplementation(
                viewModel =  qrCodeViewModel,
                activityLauncher = this
            )
        )

        biometricApi = BiometricApiImplementation(this)
        BiometricAuthApi.setUp(
            binaryMessenger = binaryMessenger,
            api = biometricApi
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BiometricApiImplementation.REQUEST_CODE_BIOMETRIC) {
            biometricApi.handleBiometricResult(resultCode, data)
        }
    }

    override fun launchActivity(activityClass: Class<*>, extras: Bundle?) {
        val intent = Intent(this, activityClass)

        // Attach extras if provided
        extras?.let { intent.putExtras(it) }

        startActivity(intent)
    }


}