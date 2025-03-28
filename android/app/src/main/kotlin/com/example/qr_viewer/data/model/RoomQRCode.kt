package com.example.qr_viewer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_codes")
data class RoomQRCode(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rawValue: String,
    val timestamp: Long = System.currentTimeMillis()
)
