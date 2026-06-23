package mx.edu.utng.memorymatch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.MaterialTheme
import mx.edu.utng.memorymatch.presentation.board.BoardScreen
import mx.edu.utng.memorymatch.presentation.board.MemoryViewModel
import mx.edu.utng.memorymatch.presentation.board.MemoryViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            val vm: MemoryViewModel = viewModel(
                factory = MemoryViewModelFactory(applicationContext)
            )
            MaterialTheme {
                BoardScreen(viewModel = vm)
            }
        }
    }
}