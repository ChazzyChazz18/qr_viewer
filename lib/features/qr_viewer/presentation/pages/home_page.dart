import 'package:flutter/material.dart';
import 'package:qr_viewer/features/qr_viewer/camera_service.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/bloc/qr_viewer_bloc.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/widgets/scan_button.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key, required this.title});

  final String title;

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final QRViewerBloc _qrViewerBloc = QRViewerBloc();
  String qrCodeData = '';

  @override
  void initState() {
    super.initState();

    getQRCodeData();
  }

  void getQRCodeData() async {
    final result = await _qrViewerBloc.scanQRCode();
    setState(() {
      qrCodeData = result ?? "No data found";
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Column(
        children: [
          Center(
            child: Text(qrCodeData),
          ),
          Expanded(
            child: Center(
              child: ScanButton(
                onPressed: () {
                  //Pressed logic here
                  CameraService.startCamera();
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}
