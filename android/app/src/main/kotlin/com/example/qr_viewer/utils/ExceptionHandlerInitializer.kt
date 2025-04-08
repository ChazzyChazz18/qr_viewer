package com.example.qr_viewer.utils

import android.content.Context

object ExceptionHandlerInitializer {
    fun initializeExceptionHandler(context: Context) {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHandler(context, defaultHandler))
    }
}