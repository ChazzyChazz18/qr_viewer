import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/mockito.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/bloc/qr_viewer_bloc.dart';
import 'package:qr_viewer/features/shared/pigeon.dart';

// Mock dependencies
class MockQRViewerBloc extends Mock implements QRViewerBloc {
  @override
  Future<String> scanQRCode() async {
    return "Mocked QR Code Result";
  }

  @override
  Future<List<QRCode>> getAllQRCodes() async {
    return [
      QRCode(
        rawValue: 'Mocked QR Code',
        timestamp: DateTime.now().millisecondsSinceEpoch,
      ),
    ];
  }
}

class MockErrorQRViewerBloc extends MockQRViewerBloc {
  @override
  Future<String> scanQRCode() async {
    throw Exception('Error during scan');
  }

  @override
  Future<List<QRCode>> getAllQRCodes() async {
    throw Exception('Error fetching QR codes');
  }
}

void main() {
  late MockQRViewerBloc mockQRViewerBloc = MockQRViewerBloc();

  group('QRViewerBloc Tests', () {
    test('scanQRCode should call the correct method and get expected results',
        () async {
      // Act
      var scanCodeResult = await mockQRViewerBloc.scanQRCode();

      // Assert
      expect(scanCodeResult, isA<String>());
      expect(scanCodeResult, "Mocked QR Code Result");
    });

    test(
        'getAllQRCodes should call the correct method and get expected results',
        () async {
      // Act
      var allQRCodesResult = await mockQRViewerBloc.getAllQRCodes();

      // Assert
      expect(allQRCodesResult, isA<List<QRCode>>());
      expect(allQRCodesResult.length, 1);
      expect(allQRCodesResult[0], isA<QRCode>());
      expect(allQRCodesResult[0].rawValue, isA<String>());
      expect(allQRCodesResult[0].rawValue, "Mocked QR Code");
    });

    test('should handle errors gracefully when scanQRCode throws an exception',
        () async {
      // Arrange
      mockQRViewerBloc = MockErrorQRViewerBloc();

      // Act
      try {
        await mockQRViewerBloc.scanQRCode();
      } catch (e) {
        // Assert
        expect(e, isA<Exception>());
        expect(e.toString(), contains('Error during scan'));
      }
    });

    test(
        'should handle errors gracefully when getAllQRCodes throws an exception',
        () async {
      // Arrange
      mockQRViewerBloc = MockErrorQRViewerBloc();

      // Act
      try {
        await mockQRViewerBloc.getAllQRCodes();
      } catch (e) {
        // Assert
        expect(e, isA<Exception>());
        expect(e.toString(), contains('Error fetching QR codes'));
      }
    });
  });
}
