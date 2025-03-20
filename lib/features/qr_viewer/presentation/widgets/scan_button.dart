import 'package:flutter/material.dart';

class ScanButton extends StatelessWidget {
  final double size;
  final VoidCallback onPressed;

  const ScanButton({
    super.key,
    this.size = 75.0,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return IconButton(
      icon: Icon(Icons.qr_code_scanner),
      iconSize: size,
      onPressed: onPressed,
      tooltip: 'Scan',
    );
  }
}
