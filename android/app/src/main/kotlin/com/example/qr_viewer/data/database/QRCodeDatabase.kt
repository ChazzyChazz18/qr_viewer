package com.example.qr_viewer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.qr_viewer.data.dao.QRCodeDao
import com.example.qr_viewer.data.model.QRCode

@Database(entities = [QRCode::class], version = 1, exportSchema = false)
abstract class QRCodeDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QRCodeDao
}
