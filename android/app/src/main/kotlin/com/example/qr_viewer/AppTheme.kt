package com.example.qr_viewer

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val appColorScheme = ColorScheme(
    primary = Color(0xFF008080), // Teal color
    onPrimary = Color.White,    // Text/icons on teal
    primaryContainer = Color(0xFF004D40), // Darker teal container
    onPrimaryContainer = Color.White, // Text/icons on primary container
    inversePrimary = Color(0xFF004D40), // Inverse of primary
    secondary = Color(0xFF00BFA5), // Lighter teal
    onSecondary = Color.White,  // Text/icons on secondary teal
    secondaryContainer = Color(0xFF80CBC4), // Container for secondary
    onSecondaryContainer = Color.Black, // Text/icons on secondary container
    tertiary = Color(0xFF03DAC6), // Accent color
    onTertiary = Color.Black,    // Text/icons on tertiary
    tertiaryContainer = Color(0xFF018786), // Container for tertiary
    onTertiaryContainer = Color.White, // Text/icons on tertiary container
    background = Color(0xFFF5F5F5), // Background color
    onBackground = Color.Black,  // Text/icons on background
    surface = Color.White,       // Surface (e.g., cards)
    onSurface = Color.Black,     // Text/icons on surface
    surfaceVariant = Color(0xFFE0E0E0), // Variant for surface
    onSurfaceVariant = Color.Black,    // Text/icons on surface variant
    surfaceTint = Color(0xFF008080),   // Tint color for surfaces
    inverseSurface = Color(0xFF303030), // Inverse surface (dark mode)
    inverseOnSurface = Color.White,    // Text/icons on inverse surface
    error = Color(0xFFB00020),   // Error (e.g., alerts)
    onError = Color.White,       // Text/icons on error
    errorContainer = Color(0xFFF2B8B5), // Error container
    onErrorContainer = Color(0xFF601410), // Text/icons on error container
    outline = Color(0xFF757575),       // Outline color
    outlineVariant = Color(0xFFBDBDBD),    // Variant for outlines
    scrim = Color(0xFF000000),         // Overlay shadow
    surfaceBright = Color(0xFFFFFFFF), // Bright surface color
    surfaceDim = Color(0xFFEEEEEE),    // Dimmed surface color
    surfaceContainer = Color(0xFFF7F7F7), // Container surface color
    surfaceContainerLow = Color(0xFFF5F5F5), // Low-priority container
    surfaceContainerHigh = Color(0xFFEFEFEF), // High-priority container
    surfaceContainerLowest = Color(0xFFFFFFFF), // Lowest-priority container
    surfaceContainerHighest = Color(0xFFDCDCDC)  // Highest-priority container
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = appColorScheme,
        typography = Typography(), // You can customize typography here
        content = content
    )
}
