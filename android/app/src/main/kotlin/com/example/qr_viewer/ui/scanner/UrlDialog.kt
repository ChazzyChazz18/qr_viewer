package com.example.qr_viewer.ui.scanner

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun UrlDialog(
    url: String?,
    onOpen: () -> Unit,
    onCancel: () -> Unit
) {
    if (url != null) {
        AlertDialog(
            onDismissRequest = onCancel,
            title = { Text(text = "Open Link") },
            text = { Text(text = "Detected URL: $url") },
            confirmButton = {
                Button(onClick = onOpen) { Text("Open") }
            },
            dismissButton = {
                Button(onClick = onCancel) { Text("Cancel") }
            }
        )
    }
}
