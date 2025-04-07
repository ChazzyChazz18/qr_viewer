package com.example.qr_viewer.di

import com.example.qr_viewer.ui.biometric.BiometricViewModel
import android.content.Context
import com.example.qr_viewer.data.database.QRCodeDatabase
import com.example.qr_viewer.data.repository.QRCodeRepository
import com.example.qr_viewer.ui.scanner.QRCodeViewModel

// Custom dependency provider for QR code scanner and biometric authentication
object DependencyProvider {
    object QRCode {
        //
        private lateinit var qrCodeDatabase: QRCodeDatabase
        private lateinit var qrCodeRepository: QRCodeRepository

        fun initialize(context: Context) {
            qrCodeDatabase = QRCodeDatabase.getDatabase(context)
            qrCodeRepository = QRCodeRepository(qrCodeDatabase.qrCodeDao())
        }

        private fun provideQRCodeRepository(): QRCodeRepository {
            return qrCodeRepository
        }

        fun provideQRCodeViewModel(): QRCodeViewModel {
            return QRCodeViewModel(provideQRCodeRepository())
        }
    }

    object Biometric {
        //
        private lateinit var biometricViewModel: BiometricViewModel

        fun initialize() {
            biometricViewModel = BiometricViewModel()
        }

        fun provideBiometricViewModel(): BiometricViewModel {
            return biometricViewModel
        }
    }

}