package com.example.qr_viewer.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.qr_viewer.data.model.QRCode


@Dao
interface QRCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQRCode(qrCode: QRCode)

    // @Query("SELECT * FROM qr_codes WHERE id = :id")
    // suspend fun getQRCodeById(id: Int): QRCode?

    @Query("SELECT * FROM qr_codes")
    fun getAllQRCodes(): LiveData<List<QRCode>>
}