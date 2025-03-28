package com.example.qr_viewer.ui.scanner

import android.content.Context
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
import com.google.mlkit.vision.barcode.BarcodeScanning
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage

@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner,
    qrCodeViewModel: QRCodeViewModel, // Add the ViewModel as a parameter
    onDetectedUrl: (String) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            setupCamera(ctx, lifecycleOwner, previewView, qrCodeViewModel, onDetectedUrl)
            previewView
        }
    )
}

private fun setupCamera(
    ctx: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    qrCodeViewModel: QRCodeViewModel, // Accept the ViewModel here
    onDetectedUrl: (String) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = androidx.camera.core.Preview.Builder().build().apply {
            surfaceProvider = previewView.surfaceProvider
        }

        val imageAnalyzer = setupImageAnalyzer(ctx, qrCodeViewModel, onDetectedUrl)

        val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
        try {
            cameraProvider.unbindAll() // Unbind previous use cases
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer // Ensure analyzer is bound
            )
        } catch (e: Exception) {
            Log.e("CameraSetup", "Error setting up camera: ${e.message}", e)
        }
    }, ContextCompat.getMainExecutor(ctx))
}

@OptIn(ExperimentalGetImage::class)
private fun setupImageAnalyzer(
    ctx: Context,
    qrCodeViewModel: QRCodeViewModel, // Accept the ViewModel here
    onDetectedUrl: (String) -> Unit
): ImageAnalysis {
    var isScanning = false
    var lastScannedCode: String? = null

    return ImageAnalysis.Builder().build().apply {
        setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
            val mediaImage = imageProxy.image
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees

            // Ensure scanning only proceeds if not already in progress or if no image
            if (isScanning || mediaImage == null) {
                imageProxy.close()
                return@setAnalyzer
            }

            isScanning = true
            val inputImage = InputImage.fromMediaImage(mediaImage, rotationDegrees)
            val scanner = BarcodeScanning.getClient()

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull { it.rawValue != null && it.rawValue != lastScannedCode }?.let { barcode ->
                        lastScannedCode = barcode.rawValue

                        // Trigger handling logic for the scanned QR code
                        handleQRCodeScanned(
                            qrCodeViewModel,
                            barcode.rawValue!!,
                            onDetectedUrl
                        ) {
                            isScanning = false // Reset scanning state when handling completes
                            lastScannedCode = null // Clear the scanned code
                        }
                    } ?: run { isScanning = false } // Reset if no valid QR code is found
                }
                .addOnFailureListener { e ->
                    Log.e("QRCode", "Error scanning code: ${e.message}", e)
                    isScanning = false // Reset scanning state on error
                }
                .addOnCompleteListener {
                    imageProxy.close() // Always close the image proxy
                }
        }
    }
}

private fun handleQRCodeScanned(
    qrCodeViewModel: QRCodeViewModel, // Use the ViewModel
    rawValue: String,
    onDetectedUrl: (String) -> Unit,
    onComplete: () -> Unit
) {
    if (rawValue.startsWith("http")) {
        onDetectedUrl(rawValue)
        Log.d("QRCode", "URL detected: $rawValue")
    } else {
        Log.d("QRCode", "Non-URL QR code detected: $rawValue")
    }

    // Save the QR code using ViewModel
    qrCodeViewModel.saveQRCode(
        com.example.qr_viewer.data.model.RoomQRCode(
            rawValue = rawValue,
            timestamp = System.currentTimeMillis()
        )
    )

    onComplete() // Ensure scanning resumes
}

