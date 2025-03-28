import 'package:flutter/widgets.dart';
import 'package:qr_viewer/features/shared/pigeon.dart';

class QRViewerBloc {
  final qrCodeApi = QRCodeApi();

  //
  Future<String?> scanQRCode() async {
    final result = await qrCodeApi.scanQRCode();
    return result;
  }

  Future<List<QRCode?>> getAllQRCodes() async {
    List<QRCode?> fetchedQRCodes = [];
    try {
      fetchedQRCodes = await qrCodeApi.getAllSavedQRCodes();
      debugPrint(
          "First code: ${fetchedQRCodes.first?.rawValue} length: ${fetchedQRCodes.length}");
    } catch (e) {
      debugPrint('Error fetching QR codes: $e');
    }

    return fetchedQRCodes;
  }
}
