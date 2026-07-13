package mx.utng.smarthealthmonitor

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import mx.utng.smarthealthmonitor.navigation.SmartHealthNavGraph

class MainActivity : AppCompatActivity() { // ✅ Cambiado de ComponentActivity a AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartHealthNavGraph()
        }
    }
}