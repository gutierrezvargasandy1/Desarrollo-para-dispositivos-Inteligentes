import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import mx.utng.smarthealthmonitor.ui.theme.SHBackground
import mx.utng.smarthealthmonitor.ui.theme.SHBackgroundDark
import mx.utng.smarthealthmonitor.ui.theme.SHError
import mx.utng.smarthealthmonitor.ui.theme.SHOnPrimary
import mx.utng.smarthealthmonitor.ui.theme.SHOnPrimaryDark
import mx.utng.smarthealthmonitor.ui.theme.SHPrimary
import mx.utng.smarthealthmonitor.ui.theme.SHPrimaryContainer
import mx.utng.smarthealthmonitor.ui.theme.SHPrimaryDark
import mx.utng.smarthealthmonitor.ui.theme.SHSecondary
import mx.utng.smarthealthmonitor.ui.theme.SHSurface
import mx.utng.smarthealthmonitor.ui.theme.SHSurfaceDark
import mx.utng.smarthealthmonitor.ui.theme.Typography
private val LightColorScheme = lightColorScheme(
    primary = SHPrimary,
    onPrimary = SHOnPrimary,
    primaryContainer= SHPrimaryContainer,
    secondary = SHSecondary,
    error = SHError,
    background = SHBackground,
    surface = SHSurface,
)
private val DarkColorScheme = darkColorScheme(
    primary = SHPrimaryDark,
    onPrimary = SHOnPrimaryDark,
    background = SHBackgroundDark,
    surface = SHSurfaceDark,
)
@Composable
fun SmartHealthMonitorTheme(
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