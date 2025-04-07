package com.example.qr_viewer.data.model

sealed class QRCodeContent {
    data class Url(val url: String) : QRCodeContent()
    data class Text(val text: String) : QRCodeContent()
}