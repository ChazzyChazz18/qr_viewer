package com.example.qr_viewer.utils

import com.example.qr_viewer.data.model.RoomQRCode
import com.example.qr_viewer.pigeon.QRCode

// Map from Room QRCodeEntity model to Pigeon-generated QRCode model
fun RoomQRCode.toPigeonQRCode(): QRCode {
    return QRCode(
        rawValue = this.rawValue,
        timestamp = this.timestamp
    )
}

// Map from Pigeon-generated QRCode model to Room QRCodeEntity model
fun QRCode.toRoomQRCode(): RoomQRCode {
    return RoomQRCode(
        rawValue = this.rawValue ?: "",
        timestamp = this.timestamp ?: System.currentTimeMillis()
    )
}