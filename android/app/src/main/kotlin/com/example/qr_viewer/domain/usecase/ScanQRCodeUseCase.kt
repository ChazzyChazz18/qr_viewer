package com.example.qr_viewer.domain.usecase

import com.example.qr_viewer.data.repository.CameraRepository
import com.google.mlkit.vision.common.InputImage

class ScanQRCodeUseCase(private val repository: CameraRepository) {
    suspend operator fun invoke(inputImage: InputImage): String? {
        return repository.scanQRCode(inputImage)
    }
}
