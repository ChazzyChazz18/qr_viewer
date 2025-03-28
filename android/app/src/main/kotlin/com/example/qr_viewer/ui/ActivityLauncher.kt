package com.example.qr_viewer.ui

import android.os.Bundle

interface ActivityLauncher {
    fun launchActivity(activityClass: Class<*>, extras: Bundle? = null)
}