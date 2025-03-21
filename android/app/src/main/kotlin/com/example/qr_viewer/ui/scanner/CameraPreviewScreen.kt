package com.example.qr_viewer.ui.scanner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri

@Composable
fun CameraPreviewScreen(lifecycleOwner: LifecycleOwner, onClose: () -> Unit) {
    val context = LocalContext.current

    var detectedUrl by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            lifecycleOwner = lifecycleOwner,
            onDetectedUrl = { detectedUrl = it }
        )
        CloseButton(onClose = onClose)

        if (detectedUrl != null) {
            UrlDialog(
                url = detectedUrl,
                onOpen = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detectedUrl))
                    context.startActivity(intent) // Open URL in browser
                    detectedUrl = null
                },
                onCancel = { detectedUrl = null }
            )
        }
    }
}
