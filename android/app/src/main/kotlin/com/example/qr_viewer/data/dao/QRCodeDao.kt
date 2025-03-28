package com.example.qr_viewer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.qr_viewer.data.model.RoomQRCode
import kotlinx.coroutines.flow.Flow

@Dao
interface QRCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQRCode(roomQrCode: RoomQRCode): Long

    @Query("SELECT * FROM qr_codes")
    fun getAllQRCodesFlow(): Flow<List<RoomQRCode>>
}