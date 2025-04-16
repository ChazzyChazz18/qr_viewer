import 'package:flutter/foundation.dart';
import 'package:qr_viewer/features/shared/pigeon.dart';

class BiometricBloc {
  // Validate if the device supports biometric authentication
  // and if the user has enrolled any biometric credentials.
  Future<bool> isBiometricAvailable() async {
    final biometricAuthApi = BiometricAuthApi();
    final result = biometricAuthApi.isBiometricAvailable();
    return result;
  }

  Future<bool> authenticate(String promptMessage) async {
    try {
      final biometricAuthApi = BiometricAuthApi();
      final result = await biometricAuthApi.authenticate(promptMessage);
      debugPrint('Biometric authentication result: $result');
      return result; // Return true only if authentication is successful
    } catch (e) {
      // Handle any exceptions and return false for failures or cancellations
      debugPrint('Biometric authentication error: $e');

      return false;
    }
  }
}
