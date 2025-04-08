package com.example.qr_viewer.pigeon

import com.example.qr_viewer.ui.biometric.BiometricViewModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.qr_viewer.R
import com.example.qr_viewer.ui.biometric.BiometricCompatActivity

// Pigeon interfaces implementations for biometric authentication
class BiometricApiImplementation(
    private val context: Context,
    private val biometricViewModel: BiometricViewModel
) : BiometricAuthApi {

    companion object { const val REQUEST_CODE_BIOMETRIC = 1001 }

    // Checks if biometric authentication is available
    override fun isBiometricAvailable(): Boolean {
        val biometricManager = androidx.biometric.BiometricManager.from(context)
        return biometricManager.canAuthenticate(
            androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
        ) == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
    }

    // Launches the biometric authentication flow
    override fun authenticate(promptMessage: String, callback: (Result<Boolean>) -> Unit) {
        if (context !is Activity) {
            callback(Result.failure(Exception("Context is not an Activity")))
            return
        }

        // Launch the biometric activity
        val intent = Intent(context, BiometricCompatActivity::class.java).apply {
            putExtra(context.getString(R.string.biometric_extra_pm), promptMessage)
        }
        context.startActivityForResult(intent, REQUEST_CODE_BIOMETRIC)

        // Launch a coroutine to wait for the result in the shared flow
        biometricViewModel.collectAuthResult(callback)
    }

    // Called from MainActivity when BiometricCompatActivity sends back a result
    fun handleBiometricResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val authResult = data?.getBooleanExtra("auth_result", false) ?: false
            biometricViewModel.emitAuthResult(Result.success(authResult))
        } else {
            biometricViewModel.emitAuthResult(Result.failure(Exception("Authentication failed")))
        }

    }
}