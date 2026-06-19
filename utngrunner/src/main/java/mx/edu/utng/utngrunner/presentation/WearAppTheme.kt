package mx.edu.utng.utngrunner.presentation

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun WearAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}