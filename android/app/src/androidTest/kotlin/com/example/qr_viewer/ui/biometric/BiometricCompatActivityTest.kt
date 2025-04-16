package com.example.qr_viewer.ui.biometric

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.qr_viewer.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BiometricCompatActivityTest {

    @Test
    fun testGetBiometricPromptInfo() {
        val scenario = ActivityScenario.launch(BiometricCompatActivity::class.java)

        scenario.onActivity { activity ->
            val intent = Intent().apply {
                putExtra(activity.getString(R.string.biometric_extra_pm), "Custom Message")
            }
            activity.intent = intent

            val promptInfo = activity.getBiometricPromptInfo()
            assertEquals("Custom Message", promptInfo.title)
            assertEquals(activity.getString(R.string.use_your_biometric_credentials_to_authenticate), promptInfo.subtitle)
        }
    }
}