import 'package:flutter/material.dart';
import 'package:qr_viewer/core/theme/pulse_button_theme.dart';

class RoundButtonWidget extends StatelessWidget {
  final String labelText;
  final IconData iconData;
  final VoidCallback onPressed;

  const RoundButtonWidget({
    super.key,
    required this.labelText,
    required this.iconData,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return ElevatedButton.icon(
      onPressed: onPressed,
      icon: Icon(
        iconData,
        size: 24,
        color: PulseButtonTheme.defaultIconColor,
      ),
      label: Text(
        labelText,
        style: TextStyle(
          color: PulseButtonTheme.defaultTextColor,
          fontSize: PulseButtonTheme.defaultFontSize,
        ),
      ),
      style: PulseButtonTheme.getStyle(),
    );
  }
}
