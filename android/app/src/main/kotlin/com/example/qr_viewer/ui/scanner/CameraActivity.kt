package com.example.qr_viewer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class CameraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CameraPreviewScreen(
                lifecycleOwner = this,
                onClose = { finish() }
            )
        }
    }
}


@Composable
fun CameraPreviewScreen(lifecycleOwner: LifecycleOwner, onClose: () -> Unit) {
    val context = LocalContext.current // Correct usage within a composable

    var detectedUrl by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            context = context,
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


@Composable
fun CameraPreview(
    context: android.content.Context,
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
                preview.setSurfaceProvider(previewView.surfaceProvider)

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

@Composable
fun CloseButton(onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { onClose() },
            modifier = Modifier
                .align(Alignment.TopStart) // Correct usage of align()
                .padding(16.dp)
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }
    }
}

@Composable
fun UrlDialog(url: String?, onOpen: () -> Unit, onCancel: () -> Unit) {
    if (url != null) {
        AlertDialog(
            onDismissRequest = onCancel,
            title = { Text(text = "Open Link") },
            text = { Text(text = "Detected URL: $url") },
            confirmButton = {
                Button(onClick = onOpen) {
                    Text("Open")
                }
            },
            dismissButton = {
                Button(onClick = onCancel) {
                    Text("Cancel")
                }
            }
        )
    }
}

// package com.example.qr_viewer

// import android.content.Intent
// import android.net.Uri
// import android.os.Bundle
// import androidx.activity.ComponentActivity
// import androidx.activity.compose.setContent
// import androidx.activity.viewModels
// import androidx.lifecycle.lifecycleScope
// import com.example.qr_viewer.ui.scanner.CameraViewModel
// import kotlinx.coroutines.launch


// class CameraActivity : ComponentActivity() {

//     // ViewModel instance
//     private val cameraViewModel: CameraViewModel by viewModels()

//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)

//         // Observe ViewModel state for QR code detection
//         cameraViewModel.detectedQRCode.observe(this) { detectedUrl ->
//             detectedUrl?.let {
//                 openUrl(it)
//                 cameraViewModel.onUrlHandled() // Clear the URL after handling
//             }
//         }

//         // Set the composable content
//         setContent {
//             CameraPreviewScreen(
//                 lifecycleOwner = this,
//                 onClose = { finish() },
//                 onImageCaptured = { inputImage ->
//                     cameraViewModel.processImage(inputImage) // Trigger QR code scanning
//                 }
//             )
//         }
//     }

//     // Function to open the detected URL
//     private fun openUrl(url: String) {
//         val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//         startActivity(intent)
//     }
// }
