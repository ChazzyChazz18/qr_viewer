package com.example.qr_viewer.ui.scanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

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