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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text('Login'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () async {
            var result = await _biometricBloc.authenticate(
              'Authenticate with biometric',
            );

            if (result) {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => const HomePage(title: 'Home Page'),
                ),
              );
            }
          },
          child: Text('Login'),
        ),
      ),
    );
  }
}
