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
import com.google.mlkit.vision.common.InputImage

@Composable
fun CameraPreviewScreen(
    lifecycleOwner: LifecycleOwner,
    onClose: () -> Unit,
    onImageCaptured: (InputImage) -> Unit
) {
    var detectedUrl by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            lifecycleOwner = lifecycleOwner,
            onImageCaptured = onImageCaptured // Captures image and sends it to the parent
        )
        CloseButton(onClose = onClose)

        if (detectedUrl != null) {
            UrlDialog(
                url = detectedUrl,
                onOpen = { /* Handle URL opening */ },
                onCancel = { detectedUrl = null }
            )
        }
    }
}
