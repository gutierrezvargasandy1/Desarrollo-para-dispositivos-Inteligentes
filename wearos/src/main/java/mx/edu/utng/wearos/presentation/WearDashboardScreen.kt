package mx.edu.utng.wearos.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import mx.edu.utng.wearos.presentation.components.WearFCCard

@Composable
fun WearDashboardScreen(
    onAlertClick: () -> Unit = {},
    onHistorialClick: () -> Unit = {},         // NUEVO
    viewModel: WearDashboardViewModel = viewModel()
) {

    val fc by viewModel.fc.collectAsState()
    val listState = rememberScalingLazyListState()

    Scaffold(
        timeText = {
            TimeText()
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState
            )
        }
    ) {

        ScalingLazyColumn(
            state    = listState,
            modifier = Modifier.fillMaxSize()
        ) {

            item {
                WearFCCard(
                    fc       = fc,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Chip(
                    label   = { Text("🚨 Alerta") },
                    onClick = onAlertClick,
                    colors  = ChipDefaults.primaryChipColors(
                        backgroundColor = MaterialTheme.colors.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // NUEVO chip de Historial
            item {
                Chip(
                    label    = { Text("📋 Historial") },
                    onClick  = onHistorialClick,
                    colors   = ChipDefaults.secondaryChipColors(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}