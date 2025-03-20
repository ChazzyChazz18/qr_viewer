import 'package:flutter/services.dart';

class CameraService {
  static const platform = MethodChannel('com.example.qr_viewer/camera');

  static Future<void> startCamera() async {
    try {
      await platform.invokeMethod('startCamera');
    } on PlatformException catch (e) {
      print("Error starting camera: ${e.message}");
    }
  }
}
