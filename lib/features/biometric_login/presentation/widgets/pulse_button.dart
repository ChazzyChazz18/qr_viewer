import 'package:flutter/material.dart';
import 'package:qr_viewer/core/theme/pulse_button_theme.dart';

class PulseButtonWidget extends StatefulWidget {
  final String labelText;
  final IconData iconData;
  final VoidCallback onPressed;

  const PulseButtonWidget({
    super.key,
    required this.labelText,
    required this.iconData,
    required this.onPressed,
  });

  @override
  State<PulseButtonWidget> createState() => _PulseButtonWidgetState();
}

class _PulseButtonWidgetState extends State<PulseButtonWidget>
    with SingleTickerProviderStateMixin {
  late AnimationController _animationController;
  late Animation<double> _buttonScaleAnimation;

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(
      vsync: this,
      duration: PulseButtonTheme.animationDuration,
    );

    _buttonScaleAnimation = Tween<double>(
      begin: 1.0,
      end: PulseButtonTheme.getMaxScale(),
    ).animate(
      CurvedAnimation(
        parent: _animationController,
        curve: Curves.easeInOut,
      ),
    );

    // Continuous scaling animation
    _animationController.repeat(reverse: true);
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return ScaleTransition(
      scale: _buttonScaleAnimation,
      child: ElevatedButton.icon(
        onPressed: widget.onPressed,
        icon: Icon(
          widget.iconData,
          size: 24,
          color: PulseButtonTheme.defaultIconColor,
        ),
        label: Text(
          widget.labelText,
          style: TextStyle(
            color: PulseButtonTheme.defaultTextColor,
            fontSize: PulseButtonTheme.defaultFontSize,
          ),
        ),
        style: PulseButtonTheme.getStyle(),
      ),
    );
  }
}
