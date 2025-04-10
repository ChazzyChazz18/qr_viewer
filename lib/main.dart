import 'package:flutter/material.dart';
import 'package:qr_viewer/features/biometric_login/presentation/pages/login.dart';

void main() {
  runApp(const QRViewer());
}

class QRViewer extends StatelessWidget {
  const QRViewer({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'QR Viewer',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.teal),
      home: const LoginPage(),
    );
  }
}
