package mx.edu.utng.wearos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices
import mx.edu.utng.wearos.presentation.theme.SmartHealthMonitorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }

        // NO ACTIVAR TODAVÍA
        // lifecycleScope.launch {
        //     HealthDataService.registrar(applicationContext)
        // }
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