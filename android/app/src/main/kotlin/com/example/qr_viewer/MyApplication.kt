package com.example.qr_viewer

import android.app.Application
import android.util.Log
import com.example.qr_viewer.utils.GlobalExceptionHandler

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Custom initialization logic
        Log.d("MyApplication", "Application started")

        // Initialize global exception handler
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHandler(this, defaultHandler))
    }
}