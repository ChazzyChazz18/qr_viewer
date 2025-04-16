package com.example.qr_viewer.ui.biometric

import android.content.Intent
import android.os.Build
import android.view.WindowInsetsController
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.qr_viewer.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BiometricCompatActivityTest {

    @Test
    fun testGetBiometricPromptInfo() {
        val scenario = ActivityScenario.launch(BiometricCompatActivity::class.java)

        scenario.onActivity { activity ->
            val intent = Intent().apply {
                putExtra(
                    activity.getString(R.string.biometric_extra_pm),
                    "Custom Message"
                )
            }
            activity.intent = intent

            val promptInfo = activity.getBiometricPromptInfo()
            assertEquals("Custom Message", promptInfo.title)
            assertEquals(activity.getString(R.string.use_your_biometric_credentials_to_authenticate), promptInfo.subtitle)
        }
    }

    @Test
    fun testSetStatusBarBackground_api30AndAbove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val scenario = ActivityScenario.launch(BiometricCompatActivity::class.java)

            scenario.onActivity { activity ->
                // Call the method being tested
                activity.setStatusBarBackground()

                // Verify the status bar appearance
                val insetsController = activity.window.insetsController
                val flags = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                assertNotNull(insetsController)
                assertTrue(
                    (insetsController?.systemBarsAppearance ?: 0) and flags == flags
                )
            }
        }
    }

    @Test
    fun testSetStatusBarBackground_apiBelow30() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val scenario = ActivityScenario.launch(BiometricCompatActivity::class.java)

            scenario.onActivity { activity ->
                activity.setStatusBarBackground()
                val expectedColor = 0xFF008080.toInt()
                assertEquals(expectedColor, activity.window.statusBarColor)
            }
        }
    }

//    @Test
//    fun testHandleBiometricPrompt_successfulAuthentication() {
//        val scenario = ActivityScenario.launch(BiometricCompatActivity::class.java)
//
//        scenario.onActivity { activity ->
//            val biometricPrompt = mock(androidx.biometric.BiometricPrompt::class.java)
//            val authCallback = spy(androidx.biometric.BiometricPrompt.AuthenticationCallback::class.java)
//
//            doAnswer {
//                authCallback.onAuthenticationSucceeded(
//                    mock(androidx.biometric.BiometricPrompt.AuthenticationResult::class.java)
//                )
//                null
//            }.`when`(biometricPrompt).authenticate(any())
//
//            biometricPrompt.authenticate(activity.getBiometricPromptInfo())
//
//            verify(authCallback).onAuthenticationSucceeded(any())
//        }
//    }

}