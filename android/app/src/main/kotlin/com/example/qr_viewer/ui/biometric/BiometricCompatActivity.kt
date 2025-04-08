package com.example.qr_viewer.ui.biometric

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.qr_viewer.R

class BiometricCompatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleBiometricPrompt()
    }

    private fun handleBiometricPrompt () {
        val biometricPrompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    sendResult(true)
                    finish()
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    sendResult(false)
                    if (errorCode == BiometricPrompt.ERROR_USER_CANCELED ||
                        errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON ||
                        errorCode == BiometricPrompt.ERROR_CANCELED) {
                        finish()
                    }
                }
                override fun onAuthenticationFailed() {
                    sendResult(false)
                }
            }
        )

        biometricPrompt.authenticate(getBiometricPromptInfo())
    }

    private fun getBiometricPromptInfo () : BiometricPrompt.PromptInfo {
        val promptMessage = intent.getStringExtra(
            getString(R.string.biometric_extra_pm)
        ) ?: getString(R.string.authentication_required)

        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(promptMessage)
            .setSubtitle(
                getString(R.string.use_your_biometric_credentials_to_authenticate)
            )
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ).build()
    }

    private fun sendResult(success: Boolean) {
        val intent = Intent().apply { putExtra("auth_result", success) }
        setResult(RESULT_OK, intent)
    }
}