package com.example.qr_viewer.pigeon

import androidx.lifecycle.viewModelScope
import com.example.qr_viewer.ui.common.ActivityLauncher
import com.example.qr_viewer.ui.scanner.CameraActivity
import com.example.qr_viewer.ui.scanner.QRCodeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// Pigeon interfaces implementations for QR code scanning
class QRCodeApiImplementation(
    private val viewModel: QRCodeViewModel,
    private val activityLauncher: ActivityLauncher
) : QRCodeApi {

    override fun scanQRCode(): String {
        // Use the launcher to open CameraActivity
        activityLauncher.launchActivity(CameraActivity::class.java)

        return "Sample QR Code Data"
    }

    override fun getAllSavedQRCodes(callback: (Result<List<QRCode?>>) -> Unit) {
        viewModel.qrCodes.onEach { qrCodes ->
            callback(Result.success(qrCodes))
        }.launchIn(viewModel.viewModelScope)
    }

}