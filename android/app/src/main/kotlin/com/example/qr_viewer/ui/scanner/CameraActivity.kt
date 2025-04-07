package com.example.qr_viewer.ui.scanner

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class CameraActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // Permission is denied
            Log.e("CameraActivity", "Camera permission was denied!")
            return@registerForActivityResult
        }
        // Permission is granted
        initializeCameraPreview()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check for camera permissions
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED
            ) {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        } else {
            initializeCameraPreview()
        }
    }

    private fun initializeCameraPreview () {
        setContent {
            CameraPreviewScreen(
                lifecycleOwner = this,
                onClose = { finish() }
            )
        }
    }
}