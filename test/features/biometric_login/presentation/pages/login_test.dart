import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/mockito.dart';
import 'package:qr_viewer/features/biometric_login/presentation/bloc/biometric_bloc.dart';
import 'package:qr_viewer/features/biometric_login/presentation/pages/login.dart';
import 'package:qr_viewer/features/biometric_login/presentation/widgets/pulse_button.dart';
import 'package:qr_viewer/features/shared/qr_viewer_appbar.dart';

// Mock for BiometricBloc
class MockBiometricBloc extends Mock implements BiometricBloc {
  @override
  Future<bool> isBiometricAvailable() async {
    // Default to true for testing purposes
    return true;
  }

  @override
  Future<bool> authenticate(String promptMessage) async {
    // Default depending of message for testing purposes
    return promptMessage == "test";
  }
}

void main() {
  late MockBiometricBloc mockBiometricBloc;

  setUp(() => mockBiometricBloc = MockBiometricBloc());

  group('LoginPage Tests', () {
    testWidgets('Renders LoginPage with expected widgets', (
      WidgetTester tester,
    ) async {
      // Build the widget
      await tester.pumpWidget(MaterialApp(home: LoginPage()));

      // Verify AppBar exists
      expect(find.byType(QrViewerAppbar), findsOneWidget);

      // Verify fingerprint icon exists
      expect(find.byIcon(Icons.fingerprint), findsOneWidget);

      // Verify Biometric Login Text exists
      expect(find.text('Biometric Login'), findsExactly(2));

      // Verify PulseButtonWidget exists
      expect(find.byType(PulseButtonWidget), findsOneWidget);
    });

    testWidgets('Validate on successful authentication flow', (
      WidgetTester tester,
    ) async {
      // Build LoginPage
      await tester.pumpWidget(MaterialApp(home: LoginPage()));

      // Tap the PulseButtonWidget to trigger authentication
      await tester.tap(find.byType(PulseButtonWidget));
      await tester.pump();

      // Validate that isBiometricAvailable & authenticate
      // functions of mockBiometricBloc return both true
      expect(await mockBiometricBloc.isBiometricAvailable(), true);
      expect(await mockBiometricBloc.authenticate("test"), true);
    });

    testWidgets('Validate on failed authentication flow', (
      WidgetTester tester,
    ) async {
      // Build LoginPage with mocked bloc
      await tester.pumpWidget(MaterialApp(home: LoginPage()));

      // Tap the PulseButtonWidget to trigger authentication
      await tester.tap(find.byType(PulseButtonWidget));
      await tester.pump();

      // Validate authenticate returns false
      expect(await mockBiometricBloc.authenticate("test_fail"), false);

      // Verify still on LoginPage
      expect(find.byType(LoginPage), findsOneWidget);
    });
  });
}
