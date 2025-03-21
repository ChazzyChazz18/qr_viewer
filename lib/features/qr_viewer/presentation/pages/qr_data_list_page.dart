import 'package:flutter/material.dart';

class QRDataListPage extends StatefulWidget {
  const QRDataListPage({super.key});

  @override
  State<QRDataListPage> createState() => _QRDataListPageState();
}

class _QRDataListPageState extends State<QRDataListPage> {
  // final QRViewerBloc _qrViewerBloc = QRViewerBloc();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text('QR Data List'),
      ),
      // body: FutureBuilder(
      //     future: _qrViewerBloc.getAllQRCodes(),
      //     builder: (context, snapshot) {
      //       if (snapshot.connectionState == ConnectionState.done) {
      //         final qrCodeList = snapshot.data as List<QRCode>;
      //         return ListView.builder(
      //           itemCount: qrCodeList.length,
      //           itemBuilder: (context, index) {
      //             final qrCode = qrCodeList[index];
      //             return ListTile(
      //               title: Text(qrCode.rawValue ?? ""),
      //               subtitle: Text(qrCode.timestamp.toString()),
      //             );
      //           },
      //         );
      //       } else {
      //         return const Center(child: CircularProgressIndicator());
      //       }
      //     }),
      body: Container(),
    );
  }
}
