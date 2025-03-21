import 'package:qr_viewer/features/shared/pigeon.dart';

class QRViewerBloc {
  //
  Future<String?> scanQRCode() async {
    final qrCodeApi = QRCodeApi();
    final result = await qrCodeApi.scanQRCode();
    return result;
  }
}
