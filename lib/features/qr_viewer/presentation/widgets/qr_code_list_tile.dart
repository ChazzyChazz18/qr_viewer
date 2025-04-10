import 'package:flutter/material.dart';

class QRCodeListTile extends StatelessWidget {
  final int index;
  final String title;
  final String subtitle;
  final Function()? onTap;

  const QRCodeListTile({
    super.key,
    required this.index,
    required this.title,
    required this.subtitle,
    this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Material(
      // Ensure InkWell has a Material ancestor for ripple effect
      color: const Color.fromARGB(255, 250, 250, 250),
      child: InkWell(
        onTap: onTap,
        // Ripple effect color
        splashColor: Colors.teal.withValues(alpha: 0.3),
        highlightColor: Colors.teal.withValues(alpha: 0.1),
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Display the index number
              Text(
                "$index.",
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                  color: Colors.teal.shade700,
                ),
              ),
              const SizedBox(width: 8),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Title text
                    Text(
                      title,
                      style: const TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                        color: Colors.black,
                      ),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      subtitle,
                      style: TextStyle(
                        fontSize: 14,
                        color: Colors.grey.shade600,
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
