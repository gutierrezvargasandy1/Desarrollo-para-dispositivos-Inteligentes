package mx.utng.smarthealthmonitor

import SmartHealthMonitorTheme
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            SmartHealthMonitorTheme {

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    LoginScreen(

                        onLoginSuccess = {

                            // TODO sesión 5:
                            // Navegar al Dashboard

                            Log.d(
                                "SmartHealth",
                                "Login exitoso"
                            )
                        }
                    )
                }
            }
        }
    }
}


// =========================
// Login Screen Preview
// =========================
@Preview(
    name = "Login - Light",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_6"
)
@Preview(
    name = "Login - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Login - Big Font",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun LoginScreenPreview() {

    SmartHealthMonitorTheme {

        LoginScreen()
    }
}