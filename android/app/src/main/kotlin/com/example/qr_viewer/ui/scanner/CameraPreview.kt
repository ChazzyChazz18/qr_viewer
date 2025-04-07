package com.example.qr_viewer.ui.scanner

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.qr_viewer.data.model.QRCodeContent
import com.example.qr_viewer.data.model.QRCodeScannerConfig
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CameraPreview(
    scannerConfig: QRCodeScannerConfig
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            setupCamera(scannerConfig, previewView)
            previewView
        }
    )
}

private fun setupCamera(
    scannerConfig: QRCodeScannerConfig,
    previewView: PreviewView
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(scannerConfig.context)
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = androidx.camera.core.Preview.Builder().build().apply {
            surfaceProvider = previewView.surfaceProvider
        }

        val imageAnalyzer = setupImageAnalyzer(scannerConfig)

        val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
        try {
            cameraProvider.unbindAll() // Unbind previous use cases
            cameraProvider.bindToLifecycle(
                scannerConfig.lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer // Ensure analyzer is bound
            )
        } catch (e: Exception) {
            Log.e("CameraSetup", "Error setting up camera: ${e.message}", e)
        }
    }, ContextCompat.getMainExecutor(scannerConfig.context))
}

@OptIn(ExperimentalGetImage::class)
private fun setupImageAnalyzer(
    scannerConfig: QRCodeScannerConfig
): ImageAnalysis {
    var lastScannedCode: String? = null
    val timeMilliseconds: Long = 2_000

    return ImageAnalysis.Builder().build().apply {
        setAnalyzer(ContextCompat.getMainExecutor(scannerConfig.context)) { imageProxy ->
            val mediaImage = imageProxy.image
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees

            // Ensure scanning only proceeds if there is an image
            if (mediaImage == null) {
                imageProxy.close()
                return@setAnalyzer
            }

            val inputImage = InputImage.fromMediaImage(mediaImage, rotationDegrees)
            val scanner = BarcodeScanning.getClient()

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull {
                        it.rawValue != null && it.rawValue != lastScannedCode
                    }?.let { barcode ->
                        if(lastScannedCode == barcode.rawValue){
                            return@let
                        }

                        lastScannedCode = barcode.rawValue

                        // Trigger handling logic for the scanned QR code
                        handleQRCodeScanned(
                            scannerConfig,
                            barcode.rawValue!!
                        ){
                            // Reset the last scanned code after the snack bar is dismissed
                            scannerConfig.lifecycleOwner.lifecycleScope.launch {
                                delay(timeMillis = timeMilliseconds)
                                lastScannedCode = null
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("QRCode", "Error scanning code: ${e.message}", e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}

// Handle the scanned QR code
private fun handleQRCodeScanned(
    scannerConfig: QRCodeScannerConfig,
    rawValue: String,
    onComplete: () -> Unit
) {
    val detectedContent = if (rawValue.startsWith("http")) {
        QRCodeContent.Url(rawValue)
    } else {
        QRCodeContent.Text(rawValue)
    }

    scannerConfig.onDetected(detectedContent) // Use the callback in the config
    Log.d("QRCode", "QR Code detected: $rawValue")

    // Save the QR code using ViewModel
    scannerConfig.qrCodeViewModel.saveQRCode(
        com.example.qr_viewer.data.model.RoomQRCode(
            rawValue = rawValue,
            timestamp = System.currentTimeMillis()
        )
    )

    onComplete() // Ensure scanning resumes
}

