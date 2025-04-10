import 'package:flutter/material.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/bloc/qr_viewer_bloc.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/widgets/round_button.dart';
import 'package:qr_viewer/features/shared/qr_viewer_appbar.dart';
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
      MaterialPageRoute(builder: (context) => const QRDataListPage()),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: QrViewerAppbar(title: widget.title),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          spacing: 32,
          children: [
            RoundButtonWidget(
              labelText: 'Scan QR Code',
              iconData: Icons.qr_code_scanner,
              onPressed: getQRCodeData,
            ),
            RoundButtonWidget(
              labelText: 'QR Code List',
              iconData: Icons.list,
              onPressed: navigateToQRDataListPage,
            ),
          ],
        ),
      ),
    );
  }
}
