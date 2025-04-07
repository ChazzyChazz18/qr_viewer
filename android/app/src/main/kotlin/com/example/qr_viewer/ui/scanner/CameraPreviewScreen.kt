package com.example.qr_viewer.ui.scanner

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import com.example.qr_viewer.R
import com.example.qr_viewer.data.model.QRCodeContent
import com.example.qr_viewer.data.model.QRCodeScannerConfig
import com.example.qr_viewer.di.DependencyProvider
import com.google.android.material.snackbar.Snackbar

@Composable
fun CameraPreviewScreen(
    lifecycleOwner: LifecycleOwner,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    val rootView = activity.findViewById<View>(android.R.id.content) // Access root view


    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            QRCodeScannerConfig(
                context = context,
                lifecycleOwner = lifecycleOwner,
                qrCodeViewModel = DependencyProvider.QRCode.provideQRCodeViewModel(),
                onDetected = { qrCodeContent ->
                    when (qrCodeContent) {
                        is QRCodeContent.Url -> {
                            Snackbar.make(rootView, qrCodeContent.url, Snackbar.LENGTH_LONG)
                                .setAction(context.getString(R.string.open_url)) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(qrCodeContent.url))
                                    context.startActivity(intent) // Open URL in browser
                                }
                                .show()
                        }
                        is QRCodeContent.Text -> {
                            Snackbar.make(rootView, qrCodeContent.text, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            )
        )
        CloseButton(onClose = onClose)
    }
}
