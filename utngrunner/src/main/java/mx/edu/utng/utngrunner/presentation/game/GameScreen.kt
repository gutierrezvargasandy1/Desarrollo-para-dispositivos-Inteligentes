package mx.edu.utng.utngrunner.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.foundation.Canvas
import androidx.compose.ui.ExperimentalComposeUiApi
import mx.edu.utng.utngrunner.domain.model.GamePhase

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameScreen(viewModel: GameViewModel) {

    val state by viewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val hapticFeedback = LocalHapticFeedback.current
    var frame by remember { mutableLongStateOf(0L) }

    // ── Loop de animación ────────────────────────────────────────────
    LaunchedEffect(Unit) {
        while (true) {
            frame++
            kotlinx.coroutines.delay(16L)
        }
    }

    // ── Solicitar foco para la corona del reloj ──────────────────────
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    // ── Haptics ──────────────────────────────────────────────────────
    LaunchedEffect(Unit) {
        viewModel.hapticEvents.collect { type ->
            when (type) {
                HapticType.JUMP -> hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                HapticType.HIT -> hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
            }
        }
    }

    // ── Pantalla principal ───────────────────────────────────────────
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .focusable()
            .onRotaryScrollEvent { event ->
                // Corona hacia arriba = saltar
                // Corona hacia abajo  = deslizarse
                if (event.verticalScrollPixels < 0) viewModel.onJump()
                else viewModel.onSlide()
                true
            }
            .clickable { viewModel.onJump() }
    ) {
        // ── Canvas del juego ─────────────────────────────────────────
        Canvas(modifier = Modifier.fillMaxSize()) {
            GameRenderer.draw(
                canvas = drawContext.canvas,
                size   = size,
                state  = state,
                frame  = frame
            )
        }

        // ── Overlays según fase ──────────────────────────────────────
        when (state.phase) {
            GamePhase.IDLE -> IdleOverlay(onStart = viewModel::onJump)
            GamePhase.DEAD -> GameOverOverlay(
                score     = state.score,
                highScore = state.highScore,
                onRetry   = viewModel::onJump
            )
            else -> Unit
        }
    }
}

// ── Pantalla de inicio ───────────────────────────────────────────────
@Composable
private fun IdleOverlay(onStart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xBB000000))
            .clickable(onClick = onStart),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text       = "UTNG Runner",
                style      = MaterialTheme.typography.title3,
                color      = Color(0xFFF9A825),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text  = "Toca o gira la corona",
                style = MaterialTheme.typography.body2,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text  = "↑ Saltar  ↓ Deslizar",
                style = MaterialTheme.typography.caption3,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

// ── Pantalla de Game Over ────────────────────────────────────────────
@Composable
private fun GameOverOverlay(
    score: Int,
    highScore: Int,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCC000000))
            .clickable(onClick = onRetry),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text       = "GAME OVER",
                style      = MaterialTheme.typography.title3,
                color      = Color(0xFFE53935),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text  = "Puntos: $score",
                style = MaterialTheme.typography.body2,
                color = Color.White
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text  = "Récord: $highScore",
                style = MaterialTheme.typography.caption3,
                color = Color(0xFFF9A825)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text  = "Toca para reintentar",
                style = MaterialTheme.typography.caption3,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}