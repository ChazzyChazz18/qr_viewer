import 'dart:async';

class QRCode {
  String? rawValue;
  int? timestamp;

  QRCode({this.rawValue, this.timestamp});
}

abstract class QRCodeApi {
  Future<List<QRCode?>> getAllSavedQRCodes();
}
