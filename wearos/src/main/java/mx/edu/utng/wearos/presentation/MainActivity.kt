package mx.edu.utng.wearos.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices
import mx.edu.utng.wearos.data.SmartHealthRepository
import mx.edu.utng.wearos.presentation.theme.SmartHealthMonitorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "=== 🚀 MainActivity onCreate() ===")

        // ✅ INICIALIZAR EL REPOSITORIO AQUÍ (SOLUCIÓN DIRECTA)
        try {
            Log.d("MainActivity", "📁 Inicializando SmartHealthRepository...")
            SmartHealthRepository.init(applicationContext)
            Log.d("MainActivity", "✅ SmartHealthRepository inicializado")
        } catch (e: Exception) {
            Log.e("MainActivity", "❌ Error inicializando SmartHealthRepository", e)
        }

        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    SmartHealthMonitorTheme {
        SmartHealthWearNavGraph()
    }
}

@Preview(
    device = WearDevices.SMALL_ROUND,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    SmartHealthMonitorTheme {
        SmartHealthWearNavGraph()
    }
}