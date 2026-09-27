package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = StudyHubTeal,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF134E4A),
    onPrimaryContainer = Color(0xFF99F6E4),
    secondary = Color(0xFF38BDF8),
    onSecondary = Color(0xFF0F172A),
    tertiary = Color(0xFF4ADE80),
    background = Color(0xFF0B0F19),
    surface = Color(0xFF151D2E),
    surfaceVariant = Color(0xFF1E293B),
    onBackground = Color(0xFFF8FAFC),
    onSurface = Color(0xFFF8FAFC),
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = Color(0xFF2E3B52),
    outlineVariant = Color(0xFF1E293B)
)

private val LightColorScheme = lightColorScheme(
    primary = StudyHubTeal,
    onPrimary = Color.White,
    primaryContainer = StudyHubTealLight,
    onPrimaryContainer = StudyHubTealDark,
    secondary = AccentBlue,
    onSecondary = Color.White,
    tertiary = AccentGreen,
    background = StudyHubBackground,
    surface = StudyHubSurface,
    surfaceVariant = Color(0xFFF1F5F9),
    onBackground = StudyHubTextPrimary,
    onSurface = StudyHubTextPrimary,
    onSurfaceVariant = StudyHubTextSecondary,
    outline = StudyHubBorder,
    outlineVariant = Color(0xFFE2E8F0)
)

@Composable
fun StudyHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
