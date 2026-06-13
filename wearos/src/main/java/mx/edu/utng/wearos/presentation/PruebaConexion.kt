package mx.edu.utng.wearos.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import kotlinx.coroutines.launch
import mx.edu.utng.wearos.WearDataSender

@Composable
fun PruebaConexion() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {

            scope.launch {

                try {

                    val sender = WearDataSender(context)

                    sender.enviarFC(110)

                    Log.d(
                        "WEAR_TEST",
                        "Frecuencia enviada correctamente"
                    )

                } catch (e: Exception) {

                    Log.e(
                        "WEAR_TEST",
                        "Error enviando dato",
                        e
                    )
                }
            }
        }
    ) {
        Text("Enviar FC")
    }
}