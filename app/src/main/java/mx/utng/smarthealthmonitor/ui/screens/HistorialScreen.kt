package mx.utng.smarthealthmonitor.ui.screens

import SmartHealthMonitorTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.utng.smarthealthmonitor.ui.components.FilaHistorial
import mx.utng.smarthealthmonitor.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    onBack: () -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {

    val lecturas by viewModel.historial.collectAsState()

    SmartHealthMonitorTheme {

        Scaffold(

            topBar = {
                TopAppBar(
                    title = {
                        Text("Historial de FC")
                    },

                    navigationIcon = {
                        IconButton(
                            onClick = onBack
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Regresar"
                            )
                        }
                    }
                )
            }

        ) { paddingValues ->

            if (lecturas.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No hay lecturas aún.\nEspera datos del wearable.",
                        textAlign = TextAlign.Center
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {

                    item {

                        Text(
                            text = "${lecturas.size} lecturas registradas",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    items(
                        items = lecturas,
                        key = { it.id }
                    ) { lectura ->

                        FilaHistorial(
                            lectura = lectura
                        )
                    }
                }
            }
        }
    }
}