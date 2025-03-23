package com.example.qr_viewer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qr_viewer.data.dao.QRCodeDao
import com.example.qr_viewer.data.model.QRCode

@Database(entities = [QRCode::class], version = 1, exportSchema = false)
abstract class QRCodeDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QRCodeDao

    companion object {
        @Volatile
        private var INSTANCE: QRCodeDatabase? = null

        fun getDatabase(context: Context): QRCodeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QRCodeDatabase::class.java,
                    "qr_code_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}