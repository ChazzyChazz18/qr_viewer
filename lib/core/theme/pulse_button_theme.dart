import 'package:flutter/material.dart';

class PulseButtonTheme {
  static const Color defaultButtonColor = Colors.teal;
  static const Color defaultTextColor = Colors.white;
  static const Color defaultIconColor = Colors.white;
  static const double defaultFontSize = 18.0;
  static const double defaultPaddingHorizontal = 24.0;
  static const double defaultPaddingVertical = 16.0;
  static const double defaultBorderRadius = 30.0;
  static const double defaultMaxScale = 1.05;
  static const Duration defaultAnimationDuration = Duration(milliseconds: 750);

  /// Returns a ButtonStyle for customizing the ElevatedButton
  static ButtonStyle getStyle({
    Color? buttonColor,
    double? borderRadius,
    double? paddingHorizontal,
    double? paddingVertical,
  }) {
    return ElevatedButton.styleFrom(
      padding: EdgeInsets.symmetric(
        horizontal: paddingHorizontal ?? defaultPaddingHorizontal,
        vertical: paddingVertical ?? defaultPaddingVertical,
      ),
      shape: RoundedRectangleBorder(
        borderRadius:
            BorderRadius.circular(borderRadius ?? defaultBorderRadius),
      ),
      backgroundColor: buttonColor ?? defaultButtonColor,
    );
  }

  static const Duration animationDuration = defaultAnimationDuration;

  static double getMaxScale() {
    return defaultMaxScale;
  }
}
