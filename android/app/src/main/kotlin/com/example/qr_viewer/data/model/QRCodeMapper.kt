package com.example.qr_viewer.data.model

// Map from Room QRCodeEntity to Pigeon-generated QRCode
fun QRCode.toPigeonQRCode(): com.example.qr_viewer.QRCode {
    return com.example.qr_viewer.QRCode(
        rawValue = this.rawValue,
        timestamp = this.timestamp
    )
}

// Map from Pigeon-generated QRCode to Room QRCodeEntity
fun com.example.qr_viewer.QRCode.toRoomQRCode(): QRCode {
    return QRCode(
        rawValue = this.rawValue ?: "",
        timestamp = this.timestamp ?: System.currentTimeMillis()
    )
}