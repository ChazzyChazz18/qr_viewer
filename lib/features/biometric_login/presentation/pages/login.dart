import 'package:flutter/material.dart';
import 'package:qr_viewer/features/biometric_login/presentation/bloc/biometric_bloc.dart';
import 'package:qr_viewer/features/biometric_login/presentation/widgets/pulse_button.dart';
import 'package:qr_viewer/home_page.dart';
import 'package:qr_viewer/features/shared/qr_viewer_appbar.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage>
    with SingleTickerProviderStateMixin {
  final BiometricBloc _biometricBloc = BiometricBloc();
  late AnimationController _animationController;

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 750),
    );
    _animationController.repeat(reverse: true);
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  void _navigateToHomePage() {
    Navigator.of(context).pushReplacement(
      MaterialPageRoute(
        builder: (context) => const HomePage(title: 'Home'),
      ),
    );
  }

  void _handleAuthentication() async {
    var isAuthenticated = await _biometricBloc.authenticate(
      'Authenticate',
    );

    if (isAuthenticated) _navigateToHomePage();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: QrViewerAppbar(title: 'Biometric Login'),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.fingerprint,
              size: 100,
              color: Colors.teal.shade300,
            ),
            const SizedBox(height: 20),
            Text(
              'Biometric Login',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: Colors.teal.shade700,
              ),
            ),
            const SizedBox(height: 40),
            PulseButtonWidget(
              labelText: "Authenticate",
              iconData: Icons.login,
              onPressed: _handleAuthentication,
            ),
          ],
        ),
      ),
    );
  }
}
