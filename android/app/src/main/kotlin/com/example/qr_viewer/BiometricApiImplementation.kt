package com.example.qr_viewer

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity

class BiometricApiImplementation(private val context: Context) : BiometricAuthApi {

    override fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    override fun authenticate(promptMessage: String): Boolean {
        if (context is FlutterActivity) {
            val intent = Intent(context, BiometricCompatActivity::class.java).apply {
                putExtra("PROMPT_MESSAGE", promptMessage)
            }
            context.startActivityForResult(intent, REQUEST_CODE_BIOMETRIC)
            return true
        } else {
            throw IllegalStateException("Context is not FlutterActivity")
        }
    }

    companion object {
        const val REQUEST_CODE_BIOMETRIC = 1001
    }
}