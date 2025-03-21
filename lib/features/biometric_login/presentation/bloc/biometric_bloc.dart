import 'package:qr_viewer/features/shared/pigeon.dart';

class BiometricBloc {
  //
  Future<bool> isBiometricAvailable() async {
    final biometricAuthApi = BiometricAuthApi();
    final result = biometricAuthApi.isBiometricAvailable();
    return result;
  }

  Future<bool> authenticate(String promptMessage) async {
    final biometricAuthApi = BiometricAuthApi();
    final result = biometricAuthApi.authenticate(promptMessage);
    return result;
  }
}
