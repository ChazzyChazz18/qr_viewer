package com.example.qr_viewer

import android.content.Context
import android.content.Intent
import android.app.Activity
import io.flutter.embedding.android.FlutterActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BiometricApiImplementation(private val context: Context) : BiometricAuthApi {

    private val _authResultFlow = MutableSharedFlow<Result<Boolean>>(extraBufferCapacity = 1)
    val authResultFlow = _authResultFlow // Exposing flow if needed externally

    override fun isBiometricAvailable(): Boolean {
        val biometricManager = androidx.biometric.BiometricManager.from(context)
        return biometricManager.canAuthenticate(
            androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
        ) == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
    }

    override fun authenticate(promptMessage: String, callback: (Result<Boolean>) -> Unit) {
        if (context !is Activity) {
            callback(Result.failure(Exception("Context is not an Activity")))
            return
        }

        // Launch the biometric activity
        val intent = Intent(context, BiometricCompatActivity::class.java).apply {
            putExtra("PROMPT_MESSAGE", promptMessage)
        }
        context.startActivityForResult(intent, REQUEST_CODE_BIOMETRIC)

        // Launch a coroutine to wait for the result in the shared flow
        GlobalScope.launch {
            try {
                // Collect the result emitted by the shared flow
                val result = authResultFlow.first()
                callback(result)
            } catch (e: Exception) {
                callback(Result.failure(e))
            }
        }
    }

    // Called from MainActivity when BiometricCompatActivity sends back a result
    fun handleBiometricResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val authResult = data?.getBooleanExtra("auth_result", false) ?: false
            _authResultFlow.tryEmit(Result.success(authResult))
        } else {
            _authResultFlow.tryEmit(Result.success(false))
        }
    }

    companion object {
        const val REQUEST_CODE_BIOMETRIC = 1001
    }
}