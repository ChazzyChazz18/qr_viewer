package com.example.qr_viewer.utils

import android.content.Intent
import com.example.qr_viewer.MainActivity
import com.example.qr_viewer.di.DependencyProvider
import com.example.qr_viewer.pigeon.BiometricApiImplementation
import com.example.qr_viewer.pigeon.BiometricAuthApi
import com.example.qr_viewer.pigeon.QRCodeApi
import com.example.qr_viewer.pigeon.QRCodeApiImplementation
import com.example.qr_viewer.ui.common.ActivityLauncher
import io.flutter.plugin.common.BinaryMessenger

class PigeonApiInitializer(private val activityLauncher: ActivityLauncher) {

    private lateinit var biometricApi: BiometricApiImplementation

    fun initialize(binaryMessenger: BinaryMessenger) {
        setUpQRCodeApi(binaryMessenger)
        setUpBiometricApi(binaryMessenger)
    }

    private fun setUpQRCodeApi(binaryMessenger: BinaryMessenger) {
        QRCodeApi.setUp(
            binaryMessenger,
            QRCodeApiImplementation(
                viewModel = DependencyProvider.QRCode.provideQRCodeViewModel(),
                activityLauncher = activityLauncher
            )
        )
    }

    private fun setUpBiometricApi(binaryMessenger: BinaryMessenger) {
        biometricApi = BiometricApiImplementation(
            activityLauncher as MainActivity,
            DependencyProvider.Biometric.provideBiometricViewModel()
        )

        BiometricAuthApi.setUp(
            binaryMessenger = binaryMessenger,
            api = biometricApi
        )
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BiometricApiImplementation.REQUEST_CODE_BIOMETRIC) {
            biometricApi.handleBiometricResult(resultCode, data)
        }
    }
}