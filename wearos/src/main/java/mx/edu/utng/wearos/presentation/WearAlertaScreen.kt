package mx.edu.utng.wearos.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.wear.compose.material.*

@Composable
fun WearAlertaScreen(
    fc: Int,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.spacedBy(
            8.dp,
            Alignment.CenterVertically
        )
    ) {

        Text(
            text = "FC: $fc bpm",
            style = MaterialTheme.typography.title3,
            color = MaterialTheme.colors.error
        )

        Text(
            text = "¿Enviar alerta?"
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = onConfirmar
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Confirmar"
                )
            }

            Button(
                onClick = onCancelar
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancelar"
                )
            }
        }
    }
}