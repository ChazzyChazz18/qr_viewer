import 'package:flutter/material.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/bloc/qr_viewer_bloc.dart';

class QRDataListPage extends StatefulWidget {
  const QRDataListPage({super.key});

  @override
  State<QRDataListPage> createState() => _QRDataListPageState();
}

class _QRDataListPageState extends State<QRDataListPage> {
  final QRViewerBloc _qrViewerBloc = QRViewerBloc();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text('QR Data List'),
      ),
      body: Scaffold(
        body: FutureBuilder(
          future: _qrViewerBloc.getAllQRCodes(),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else if (snapshot.hasError) {
              return const Center(
                child: Text('Error fetching QR codes'),
              );
            } else {
              final qrCodes = snapshot.data;
              return ListView.builder(
                itemCount: qrCodes!.length,
                itemBuilder: (context, index) {
                  final qrCode = qrCodes[index];
                  return ListTile(
                    title: Text(qrCode?.rawValue ?? 'No data'),
                    subtitle:
                        Text(qrCode?.timestamp.toString() ?? 'No timestamp'),
                  );
                },
              );
            }
          },
        ),
      ),
    );
  }
}
