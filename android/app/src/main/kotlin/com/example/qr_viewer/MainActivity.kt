package com.example.qr_viewer

import android.content.Intent
import android.os.Bundle
import com.example.qr_viewer.di.DependencyProvider
import com.example.qr_viewer.utils.ExceptionHandlerInitializer
import com.example.qr_viewer.utils.PigeonApiInitializer
import com.example.qr_viewer.ui.common.ActivityLauncher
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity(), ActivityLauncher {

    private lateinit var pigeonApiInitializer: PigeonApiInitializer

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Initialize exception handler
        ExceptionHandlerInitializer.initializeExceptionHandler(this)

        // Initialize dependencies
        DependencyProvider.QRCode.initialize(this.applicationContext)
        DependencyProvider.Biometric.initialize()

        // Delegate API initialization
        pigeonApiInitializer = PigeonApiInitializer(this)
        pigeonApiInitializer.initialize(flutterEngine.dartExecutor.binaryMessenger)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        pigeonApiInitializer.handleActivityResult(requestCode, resultCode, data)
    }

    override fun launchActivity(activityClass: Class<*>, extras: Bundle?) {
        val intent = Intent(this, activityClass)
        extras?.let { intent.putExtras(it) }
        startActivity(intent)
    }
}