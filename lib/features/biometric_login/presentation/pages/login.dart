import 'package:flutter/material.dart';
import 'package:qr_viewer/features/biometric_login/presentation/bloc/biometric_bloc.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/pages/home_page.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final BiometricBloc _biometricBloc = BiometricBloc();

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
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text('Login'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: _handleAuthentication,
          child: Text('Login'),
        ),
      ),
    );
  }
}
