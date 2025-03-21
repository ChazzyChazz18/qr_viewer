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
import com.google.mlkit.vision.barcode.BarcodeScanning
import android.util.Log
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage

@OptIn(ExperimentalGetImage::class)
@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner,
    onDetectedUrl: (String) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder().build()
                preview.surfaceProvider = previewView.surfaceProvider

                val imageAnalyzer = ImageAnalysis.Builder().build().apply {
                    setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val inputImage = InputImage.fromMediaImage(mediaImage, rotationDegrees)
                            val scanner = BarcodeScanning.getClient()
                            scanner.process(inputImage)
                                .addOnSuccessListener { barcodes ->
                                    for (barcode in barcodes) {
                                        val rawValue = barcode.rawValue
                                        if (rawValue != null && rawValue.startsWith("http")) {
                                            onDetectedUrl(rawValue)
                                            Log.d("QRCode", "URL detected: $rawValue")
                                        } else {
                                            Toast.makeText(ctx, "Code detected: $rawValue", Toast.LENGTH_SHORT).show()
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

                val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}
