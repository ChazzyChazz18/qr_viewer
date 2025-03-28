package com.example.qr_viewer

import androidx.lifecycle.viewModelScope
import com.example.qr_viewer.ui.ActivityLauncher
import com.example.qr_viewer.ui.scanner.CameraActivity
import com.example.qr_viewer.ui.scanner.QRCodeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class QRCodeApiImplementation(
    private val viewModel: QRCodeViewModel,
    private val activityLauncher: ActivityLauncher
) : QRCodeApi {

    companion object {
        const val SCAN_QR_REQUEST_CODE = 1001
    }

    override fun scanQRCode(): String {
        // Use the launcher to open CameraActivity
        activityLauncher.launchActivity(CameraActivity::class.java)

        return "Sample QR Code Data"
    }

    override fun getAllSavedQRCodes(callback: (Result<List<QRCode?>>) -> Unit) {
        viewModel.qrCodes.onEach { qrCodes ->
            callback(Result.success(qrCodes))
        }.launchIn(viewModel.viewModelScope) // Use the ViewModel's scope
    }

}