// This file is used to generate the pigeon class
// for the communication between Flutter and Native code

import 'package:pigeon/pigeon.dart';

class QRCode {
  String? rawValue;
  int? timestamp;
}

@HostApi()
abstract class QRCodeApi {
  String? scanQRCode();

  @async
  List<QRCode?> getAllSavedQRCodes();
}

@HostApi()
abstract class BiometricAuthApi {
  bool isBiometricAvailable();

  @async
  bool authenticate(String promptMessage);
}
