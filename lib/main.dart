import 'package:flutter/material.dart';

import 'features/qr_viewer/presentation/pages/home_page.dart';

void main() {
  runApp(const QRViewer());
}

class QRViewer extends StatelessWidget {
  const QRViewer({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'QR Viewer',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const HomePage(title: 'QR Viewer Home Page'),
    );
  }
}