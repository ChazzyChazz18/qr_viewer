import 'package:flutter/material.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/bloc/qr_viewer_bloc.dart';
import 'package:qr_viewer/features/qr_viewer/presentation/widgets/qr_code_list_tile.dart';
import 'package:qr_viewer/features/shared/qr_viewer_appbar.dart';
import 'package:qr_viewer/utils/date_utils.dart';

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
      appBar: QrViewerAppbar(title: 'QR Code List'),
      body: Scaffold(
        body: FutureBuilder(
          future: _qrViewerBloc.getAllQRCodes(),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(child: CircularProgressIndicator());
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
                  return Column(
                    children: [
                      QRCodeListTile(
                        index: index + 1,
                        title: qrCode?.rawValue ?? 'No data',
                        subtitle:
                            formatMillisecondsToDate(qrCode!.timestamp ?? 0),
                        onTap: () {
                          // Handle tap event if needed
                          debugPrint('Tapped on QR code: ${qrCode.rawValue}');
                        },
                      ),
                      // Prevents adding a divider after the last item
                      if (index < qrCodes.length - 1)
                        const Divider(
                          height: 1,
                          thickness: 1,
                          color: Color.fromARGB(255, 226, 226, 226),
                        ),
                    ],
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
