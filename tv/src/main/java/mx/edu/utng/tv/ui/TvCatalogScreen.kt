package mx.edu.utng.tv.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import mx.edu.utng.tv.domain.model.LecturaFC

@Composable
fun TvCatalogScreen(
    onCardClick: (Int) -> Unit = {},
    viewModel: TvViewModel = viewModel(
        factory = TvViewModelFactory(LocalContext.current)
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lecturasDemo = listOf(
        LecturaFC(id = 1, bpm = 72, estado = "Normal", hora = "10:00"),
        LecturaFC(id = 2, bpm = 85, estado = "Normal", hora = "10:05"),
        LecturaFC(id = 3, bpm = 110, estado = "Elevada", hora = "10:10"),
        LecturaFC(id = 4, bpm = 75, estado = "Normal", hora = "10:15")
    )

    val lecturas = if (state.lecturas.isEmpty()) {
        lecturasDemo
    } else {
        state.lecturas
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1B4A))
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            return@Box
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            state = rememberLazyListState()
        ) {
            item {
                RowSection(
                    title = "⚡ Estado Actual — ${lecturas.lastOrNull()?.bpm ?: 0} bpm"
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(lecturas.takeLast(3)) { lectura ->
                            FcCardItem(
                                lectura = lectura,
                                onClick = { onCardClick(lectura.id.toInt()) }
                            )
                        }
                    }
                }
            }

            item {
                RowSection(
                    title = "📋 Historial FC"
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(lecturas) { lectura ->
                            FcCardItem(
                                lectura = lectura,
                                onClick = { onCardClick(lectura.id.toInt()) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        content()
    }
}