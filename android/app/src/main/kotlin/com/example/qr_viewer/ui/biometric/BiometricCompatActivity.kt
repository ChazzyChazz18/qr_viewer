package com.example.qr_viewer.ui.biometric

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.qr_viewer.AppTheme
import com.example.qr_viewer.R

class BiometricCompatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the activity fullscreen and set the status bar color
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set the status bar color
        setStatusBarBackground()

        // Set the Compose content for the background
        setContent {
            AppTheme {
                BiometricBackgroundScreen()
            }
        }

        handleBiometricPrompt()
    }

    @Suppress("DEPRECATION")
    fun setStatusBarBackground() {
        window.decorView.post {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // API 30+
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                // Fallback for older Android versions
                // Marked as Deprecated in API 30 for
                // the whole android system reason for
                // the suppression
                window.statusBarColor = Color(0xFF008080).toArgb()
            }
        }
    }

    @VisibleForTesting
    private fun handleBiometricPrompt() {
        val biometricPrompt = androidx.biometric.BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    sendResult(true)
                    finish()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    sendResult(false)
                    if (errorCode == androidx.biometric.BiometricPrompt.ERROR_USER_CANCELED ||
                        errorCode == androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON ||
                        errorCode == androidx.biometric.BiometricPrompt.ERROR_CANCELED
                    ) {
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

    @VisibleForTesting
    fun getBiometricPromptInfo(): androidx.biometric.BiometricPrompt.PromptInfo {
        val promptMessage = intent.getStringExtra(getString(R.string.biometric_extra_pm))
            ?: getString(R.string.authentication_required)

        return androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle(promptMessage)
            .setSubtitle(getString(R.string.use_your_biometric_credentials_to_authenticate))
            .setAllowedAuthenticators(
                androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ).build()
    }

    @VisibleForTesting
    fun sendResult(success: Boolean) {
        val intent = Intent().apply { putExtra("auth_result", success) }
        setResult(RESULT_OK, intent)
    }
}

@Composable
fun BiometricBackgroundScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary // Updated to teal
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top, // Align content at the top
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp)) // Space at the top
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = "Biometric Authentication",
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Biometric Authentication",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            )

            Text(
                text = "Please use your fingerprint or credentials to proceed.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
    }
}