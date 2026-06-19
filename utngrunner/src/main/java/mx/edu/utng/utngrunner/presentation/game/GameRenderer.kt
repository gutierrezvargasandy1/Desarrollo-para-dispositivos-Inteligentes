package mx.edu.utng.utngrunner.presentation.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import mx.edu.utng.utngrunner.domain.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sin

object GameRenderer {

    fun draw(canvas: Canvas, size: Size, state: GameState, frame: Long) {
        drawBackground(canvas, size, state.level)
        drawCoins(canvas, state.coins, frame)
        drawObstacles(canvas, state.obstacles, size)
        drawPlayer(canvas, state.player, frame)
        drawHUD(canvas, size, state)
    }

    // ── Fondo ────────────────────────────────────────────────────────
    private fun drawBackground(canvas: Canvas, size: Size, level: Int) {
        val bgColor = when (level) {
            1 -> Color(0xFF0D1B2A)
            2 -> Color(0xFF1A1A2E)
            3 -> Color(0xFF16213E)
            4 -> Color(0xFF0F3460)
            else -> Color(0xFF1B0000)
        }
        val paint = Paint().apply { color = bgColor }
        canvas.drawRect(Rect(Offset.Zero, size), paint)

        // Piso
        val floorPaint = Paint().apply { color = Color(0xFF1A237E) }
        canvas.drawRect(
            Rect(0f, size.height * 0.82f, size.width, size.height),
            floorPaint
        )

        // Línea del piso
        val linePaint = Paint().apply { color = Color(0xFFF9A825) }
        canvas.drawRect(
            Rect(0f, size.height * 0.82f, size.width, size.height * 0.82f + 2f),
            linePaint
        )
    }

    // ── Jugador ──────────────────────────────────────────────────────
    private fun drawPlayer(canvas: Canvas, player: Player, frame: Long) {
        val alpha = if (player.isInvincible && (frame / 4) % 2 == 0L) 0.3f else 1f
        val yPos  = player.y

        // Cuerpo
        val bodyPaint = Paint().apply {
            color = Color(0xFFE65100).copy(alpha = alpha)
        }
        canvas.drawRect(
            Rect(player.x - 6f, yPos - 10f, player.x + 14f, yPos + 14f),
            bodyPaint
        )

        // Casco UTNG
        val helmetPaint = Paint().apply {
            color = Color(0xFF1A237E).copy(alpha = alpha)
        }
        canvas.drawRect(
            Rect(player.x - 5f, yPos - 24f, player.x + 13f, yPos - 10f),
            helmetPaint
        )

        // Visera del casco
        val visorPaint = Paint().apply {
            color = Color(0xFF4FC3F7).copy(alpha = alpha)
        }
        canvas.drawRect(
            Rect(player.x - 2f, yPos - 22f, player.x + 10f, yPos - 14f),
            visorPaint
        )

        // Piernas (animadas al correr)
        val legPaint = Paint().apply {
            color = Color(0xFF37474F).copy(alpha = alpha)
        }
        if (player.isSliding) {
            // Deslizarse — piernas hacia adelante
            canvas.drawRect(
                Rect(player.x - 6f, yPos + 10f, player.x + 14f, yPos + 18f),
                legPaint
            )
        } else {
            val swing = sin(frame * 0.3f) * 6f
            canvas.drawRect(
                Rect(player.x - 4f, yPos + 14f, player.x + 2f, yPos + 26f + swing),
                legPaint
            )
            canvas.drawRect(
                Rect(player.x + 4f, yPos + 14f, player.x + 10f, yPos + 26f - swing),
                legPaint
            )
        }
    }

    // ── Obstáculos ───────────────────────────────────────────────────
    private fun drawObstacles(
        canvas: Canvas, obstacles: List<Obstacle>, size: Size
    ) {
        val floor = size.height * 0.82f

        obstacles.forEach { obs ->
            val color = when (obs.type) {
                ObstacleType.TAREA  -> Color(0xFFE53935)
                ObstacleType.EXAMEN -> Color(0xFFD81B60)
                ObstacleType.BUG    -> Color(0xFF6A1B9A)
                ObstacleType.REPO   -> Color(0xFF1565C0)
            }
            val paint = Paint().apply { this.color = color }
            canvas.drawRect(
                Rect(
                    obs.x,
                    floor - obs.height,
                    obs.x + obs.width,
                    floor
                ),
                paint
            )

            // Etiqueta del obstáculo (pequeño rect blanco encima)
            val labelPaint = Paint().apply { this.color = Color.White.copy(alpha = 0.9f) }
            canvas.drawRect(
                Rect(obs.x, floor - obs.height - 10f,
                    obs.x + obs.width, floor - obs.height),
                labelPaint
            )
        }
    }

    // ── Monedas ──────────────────────────────────────────────────────
    private fun drawCoins(canvas: Canvas, coins: List<Coin>, frame: Long) {
        coins.filter { !it.collected }.forEach { coin ->
            val bounce = sin(frame * 0.1f + coin.phase) * 4f
            val paint  = Paint().apply { color = Color(0xFFF9A825) }
            canvas.drawCircle(
                Offset(coin.x, coin.y + bounce),
                8f,
                paint
            )
            // Brillo interior
            val shinePaint = Paint().apply { color = Color(0xFFFFF9C4) }
            canvas.drawCircle(
                Offset(coin.x - 2f, coin.y + bounce - 2f),
                3f,
                shinePaint
            )
        }
    }

    // ── HUD (cabecera con puntuación y vidas) ────────────────────────
    private fun drawHUD(canvas: Canvas, size: Size, state: GameState) {
        val cx = size.width / 2f

        // Hora del sistema
        drawCenteredRect(canvas, cx, 18f, 60f, 14f, Color(0x88000000))

        // Vidas (corazones)
        repeat(state.lives) { i ->
            drawHeart(canvas, 10f + i * 18f, 34f)
        }

        // Frecuencia cardíaca
        val bpmPaint = Paint().apply { color = Color(0xFFEF9A9A) }
        canvas.drawCircle(Offset(size.width - 20f, 30f), 6f, bpmPaint)

        // Puntuación
        val scorePaint = Paint().apply { color = Color(0xFFF9A825) }
        canvas.drawRect(
            Rect(cx - 30f, size.height - 24f, cx + 30f, size.height - 10f),
            scorePaint
        )
    }

    // ── Helpers ──────────────────────────────────────────────────────
    private fun drawHeart(canvas: Canvas, x: Float, y: Float) {
        val paint = Paint().apply { color = Color(0xFFE53935) }
        canvas.drawRect(Rect(x, y - 6f, x + 10f, y + 4f), paint)
        canvas.drawRect(Rect(x - 3f, y - 9f, x + 5f, y), paint)
        canvas.drawRect(Rect(x + 5f, y - 9f, x + 13f, y), paint)
    }

    private fun drawCenteredRect(
        canvas: Canvas, cx: Float, cy: Float,
        w: Float, h: Float, color: Color
    ) {
        val paint = Paint().apply { this.color = color }
        canvas.drawRect(Rect(cx - w / 2f, cy - h / 2f, cx + w / 2f, cy + h / 2f), paint)
    }

    private fun getSystemTime(): String =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
}