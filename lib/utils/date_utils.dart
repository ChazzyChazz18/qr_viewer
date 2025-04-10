import 'package:intl/intl.dart';

/// Converts milliseconds since epoch to a formatted date string
String formatMillisecondsToDate(
  int milliseconds, {
  String format = 'yyyy-MM-dd HH:mm:ss',
}) {
  DateTime dateTime = DateTime.fromMillisecondsSinceEpoch(milliseconds);
  return DateFormat(format).format(dateTime);
}
