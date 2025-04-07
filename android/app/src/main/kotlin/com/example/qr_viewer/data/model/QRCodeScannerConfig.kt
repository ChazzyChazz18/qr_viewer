package com.example.qr_viewer.data.model

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.qr_viewer.ui.scanner.QRCodeViewModel

data class QRCodeScannerConfig(
    val context: Context,
    val lifecycleOwner: LifecycleOwner,
    val qrCodeViewModel: QRCodeViewModel,
    val onDetected: (QRCodeContent) -> Unit
)