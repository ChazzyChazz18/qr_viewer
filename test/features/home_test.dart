import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/pages/qr_data_list_page.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/widgets/round_button.dart';
import 'package:qr_viewer/features/shared/qr_viewer_appbar.dart';
import 'package:qr_viewer/home_page.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  group('HomePage Tests', () {
    testWidgets('Displays correct app bar title', (
      WidgetTester tester,
    ) async {
      // Build the HomePage widget
      await tester.pumpWidget(
        const MaterialApp(
          home: HomePage(title: 'QR Viewer'),
        ),
      );

      // Verify that the QrViewerAppbar displays the correct title
      expect(find.byType(QrViewerAppbar), findsOneWidget);
      expect(find.text('QR Viewer'), findsOneWidget);
    });

    testWidgets('Displays both buttons with correct labels', (
      WidgetTester tester,
    ) async {
      // Build the HomePage widget
      await tester.pumpWidget(
        const MaterialApp(
          home: HomePage(title: 'QR Viewer'),
        ),
      );

      // Verify the Scan QR Code button is displayed
      expect(find.byType(RoundButtonWidget), findsNWidgets(2));
      expect(find.text('Scan QR Code'), findsOneWidget);

      // Verify the QR Code List button is displayed
      expect(find.text('QR Code List'), findsOneWidget);
    });

    testWidgets(
        'Navigates to QRDataListPage when QR Code List button is tapped', (
      WidgetTester tester,
    ) async {
      // Build the HomePage widget wrapped in MaterialApp
      await tester.pumpWidget(
        MaterialApp(home: const HomePage(title: 'QR Viewer')),
      );

      // Verify that the QR Code List button is present
      expect(find.text('QR Code List'), findsOneWidget);

      // Tap the QR Code List button
      await tester.tap(find.text('QR Code List'));
      await tester.pump(); // Wait for one frame
      await tester.pump(); // Wait for another frame

      // Verify that QRDataListPage is now in the widget tree
      expect(find.byType(QRDataListPage), findsOneWidget);
    });
  });
}
