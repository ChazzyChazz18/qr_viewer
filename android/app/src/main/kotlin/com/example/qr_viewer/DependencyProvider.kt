package com.example.qr_viewer

import android.content.Context
import com.example.qr_viewer.data.database.QRCodeDatabase
import com.example.qr_viewer.data.repository.QRCodeRepository
import com.example.qr_viewer.ui.scanner.QRCodeViewModel

object DependencyProvider {

    private lateinit var qrCodeDatabase: QRCodeDatabase
    private lateinit var qrCodeRepository: QRCodeRepository

    fun initialize(context: Context) {
        qrCodeDatabase = QRCodeDatabase.getDatabase(context)
        qrCodeRepository = QRCodeRepository(qrCodeDatabase.qrCodeDao())
    }

    fun provideQRCodeRepository(): QRCodeRepository {
        return qrCodeRepository
    }

    fun provideQRCodeViewModel(): QRCodeViewModel {
        return QRCodeViewModel(provideQRCodeRepository())
    }
}