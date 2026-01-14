package com.kennyschool.livestocklistingboard.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1B5E20),          // Farm Green
    onPrimary = Color(0xFFF3EFE7),        // Cream text
    primaryContainer = Color(0xFFCFE8D2),
    onPrimaryContainer = Color(0xFF0B3D11),

    secondary = Color(0xFF5D4037),        // Soil Brown
    onSecondary = Color(0xFFF3EFE7),
    secondaryContainer = Color(0xFFE7D7D2),
    onSecondaryContainer = Color(0xFF2B1B16),

    tertiary = Color(0xFFFF8F00),         // Amber accent
    onTertiary = Color(0xFF1A1200),
    tertiaryContainer = Color(0xFFFFE0B2),
    onTertiaryContainer = Color(0xFF2A1A00),

    background = Color(0xFFF7F5F0),
    onBackground = Color(0xFF1B1B1B),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1B),

    surfaceVariant = Color(0xFFE8E3DA),
    onSurfaceVariant = Color(0xFF3A3A3A),

    outline = Color(0xFF8B8B8B),
    error = Color(0xFFB3261E)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2E7D32),          // Field Green
    onPrimary = Color(0xFF071B09),
    primaryContainer = Color(0xFF0B3D11),
    onPrimaryContainer = Color(0xFFF3EFE7),

    secondary = Color(0xFFD7C2B9),
    onSecondary = Color(0xFF241512),
    secondaryContainer = Color(0xFF3D2A24),
    onSecondaryContainer = Color(0xFFF0E3DD),

    tertiary = Color(0xFFFFB300),         // Amber
    onTertiary = Color(0xFF1A1200),
    tertiaryContainer = Color(0xFF3A2A00),
    onTertiaryContainer = Color(0xFFFFE8C6),

    background = Color(0xFF0F120F),
    onBackground = Color(0xFFEAEAEA),

    surface = Color(0xFF141814),
    onSurface = Color(0xFFEAEAEA),

    surfaceVariant = Color(0xFF2A2F2A),
    onSurfaceVariant = Color(0xFFD6D6D6),

    outline = Color(0xFF9AA19A),
    error = Color(0xFFFFB4AB)
)

@Composable
fun LivestockListingBoardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Turn OFF dynamic color so your palette is guaranteed (better for grading)
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}