import 'package:flutter/material.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/bloc/qr_viewer_bloc.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/pages/qr_data_list_page.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key, required this.title});

  final String title;

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final QRViewerBloc _qrViewerBloc = QRViewerBloc();

  void getQRCodeData() async => await _qrViewerBloc.scanQRCode();

  void navigateToQRDataListPage() {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => const QRDataListPage(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          spacing: 32,
          children: [
            ElevatedButton(
              onPressed: getQRCodeData,
              child: Text('Scan QR Code'),
            ),
            ElevatedButton(
              onPressed: navigateToQRDataListPage,
              child: Text('QR Code List'),
            ),
          ],
        ),
      ),
    );
  }
}
