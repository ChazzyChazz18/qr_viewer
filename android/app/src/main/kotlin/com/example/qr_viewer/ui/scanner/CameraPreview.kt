package com.example.qr_viewer.ui.scanner

import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.common.InputImage
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner,
    onImageCaptured: (InputImage) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            // Camera setup and QR code analysis logic
            // Call onImageCaptured(inputImage) when an image is captured
            previewView
        }
    )
}
