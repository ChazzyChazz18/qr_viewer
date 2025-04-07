package com.example.qr_viewer

import android.content.Intent
import android.os.Bundle
import com.example.qr_viewer.di.DependencyProvider
import com.example.qr_viewer.pigeon.BiometricApiImplementation
import com.example.qr_viewer.pigeon.BiometricAuthApi
import com.example.qr_viewer.pigeon.QRCodeApi
import com.example.qr_viewer.pigeon.QRCodeApiImplementation
import com.example.qr_viewer.ui.common.ActivityLauncher
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BinaryMessenger

class MainActivity : FlutterActivity(), ActivityLauncher {

    private lateinit var biometricApi: BiometricApiImplementation

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Initialize dependencies
        DependencyProvider.QRCode.initialize(this.applicationContext)
        DependencyProvider.Biometric.initialize()

        initializePigeonAPIs(flutterEngine.dartExecutor.binaryMessenger)
    }

    private fun initializePigeonAPIs(binaryMessenger: BinaryMessenger) {
        setUpQRCodeApi(binaryMessenger)
        setUpBiometricApi(binaryMessenger)
    }

    private fun setUpQRCodeApi(binaryMessenger: BinaryMessenger) {
        QRCodeApi.setUp(
            binaryMessenger,
            QRCodeApiImplementation(
                viewModel =  DependencyProvider.QRCode.provideQRCodeViewModel(),
                activityLauncher = this
            )
        )
    }

    private fun setUpBiometricApi(binaryMessenger: BinaryMessenger) {
        biometricApi = BiometricApiImplementation(
            this,
            DependencyProvider.Biometric.provideBiometricViewModel()
        )

        BiometricAuthApi.setUp(
            binaryMessenger = binaryMessenger,
            api = biometricApi
        )
    }

    // Handle activity result for biometric authentication
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