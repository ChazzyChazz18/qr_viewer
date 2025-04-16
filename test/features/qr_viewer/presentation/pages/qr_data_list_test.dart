import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/mockito.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/bloc/qr_viewer_bloc.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/pages/qr_data_list_page.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/widgets/qr_code_list_tile.dart';
import 'package:qr_viewer/features/shared/pigeon.dart';
import 'package:qr_viewer/features/shared/qr_viewer_appbar.dart';

// Mock QRViewerBloc
class MockQRViewerBloc extends Mock implements QRViewerBloc {
  @override
  Future<List<QRCode?>> getAllQRCodes() async {
    // Mock data
    final List<QRCode?> qrCodes = List<QRCode>.generate(
      2,
      (index) => QRCode(
        rawValue: 'Code${index + 1}',
        timestamp: DateTime.now().millisecondsSinceEpoch - (index * 1000),
      ),
    );
    return qrCodes;
  }
}

class MocErrorQRViewerBloc extends QRViewerBloc {
  @override
  Future<List<QRCode>> getAllQRCodes() async {
    throw Exception('Error fetching QR codes');
  }
}

void main() {
  testWidgets('Displays correct app bar title', (
    WidgetTester tester,
  ) async {
    // Build the QRDataListPage wrapped in MaterialApp
    await tester.pumpWidget(const MaterialApp(home: QRDataListPage()));

    // Verify the app bar is displayed with the correct title
    expect(find.byType(QrViewerAppbar), findsOneWidget);
    expect(find.text('QR Code List'), findsOneWidget);
  });

  testWidgets('Shows loading indicator when data is being fetched', (
    WidgetTester tester,
  ) async {
    // Build the QRDataListPage wrapped in MaterialApp
    await tester.runAsync(() async {
      await tester.pumpWidget(
        const MaterialApp(home: QRDataListPage()),
      );

      // Verify that CircularProgressIndicator is shown initially
      expect(find.byType(CircularProgressIndicator), findsOneWidget);
    });
  });

  testWidgets('Shows error message when fetching data fails', (
    WidgetTester tester,
  ) async {
    // Create a mock QRViewerBloc
    final mockBloc = MocErrorQRViewerBloc();

    // Mock the QRViewerBloc to simulate an error
    await tester.runAsync(() async {
      await tester.pumpWidget(
        MaterialApp(home: QRDataListPage(mockBloc: mockBloc)),
      );

      // Simulate an error in FutureBuilder
      await tester.pump();
      await tester.pump();

      // Verify that error message is displayed
      expect(find.text('Error fetching QR codes'), findsOneWidget);
    });
  });

  testWidgets('Displays QR code list when data is fetched successfully', (
    WidgetTester tester,
  ) async {
    // Create a mock QRViewerBloc
    final mockBloc = MockQRViewerBloc();

    // Inject the mocked bloc into QRDataListPage
    await tester.pumpWidget(
      MaterialApp(
        home: QRDataListPage(
          key: const Key('qrDataListPage'), // Add a key for identification
          mockBloc: mockBloc, // Inject mock bloc
        ),
      ),
    );

    // Let the FutureBuilder resolve the future
    await tester.pump();
    await tester.pump();

    // Verify QR codes are displayed in the list
    var qrCodes = await mockBloc.getAllQRCodes();
    expect(find.byType(QRCodeListTile), findsNWidgets(qrCodes.length));
    expect(find.text('Code1'), findsOneWidget);
    expect(find.text('Code2'), findsOneWidget);
  });

  testWidgets('Tapping QRCodeListTile triggers tap event', (
    WidgetTester tester,
  ) async {
    // Create a mock QRViewerBloc
    final mockBloc = MockQRViewerBloc();

    var qrCodes = await mockBloc.getAllQRCodes();

    // Override debugPrint to capture the output
    final printLog = <String>[];
    debugPrint = (String? message, {int? wrapWidth}) {
      if (message != null) {
        printLog.add(message);
      }
    };

    // Build the QRDataListPage wrapped in MaterialApp
    await tester.runAsync(() async {
      await tester.pumpWidget(
        MaterialApp(home: QRDataListPage(mockBloc: mockBloc)),
      );

      // Simulate data being fetched successfully
      await tester.pump();
      await tester.pump();

      // Tap on the first QRCodeListTile
      await tester.tap(find.byType(QRCodeListTile).first);
      await tester.pump();

      // Verify debugPrint output
      expect(printLog[0], contains(qrCodes[0]?.rawValue));

      // Restore the original debugPrint behavior
      debugPrint = debugPrintSynchronously;
    });
  });
}
