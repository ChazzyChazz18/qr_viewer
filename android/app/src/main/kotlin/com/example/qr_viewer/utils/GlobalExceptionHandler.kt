package com.example.qr_viewer.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.qr_viewer.R

class GlobalExceptionHandler(
    context: Context,
    private val defaultHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler {

    private val context: Context = context.applicationContext

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        // Log the exception
        Log.e(context.getString(R.string.globalexceptionhandler), "Unhandled Exception: ${throwable.message}")

        // Perform custom actions, like restarting the app, showing a dialog, etc.
        Toast.makeText(context, "Unhandled Exception: ${throwable.message}", Toast.LENGTH_LONG).show()

        // Call the default handler if needed
        // defaultHandler?.uncaughtException(thread, throwable)
    }
}