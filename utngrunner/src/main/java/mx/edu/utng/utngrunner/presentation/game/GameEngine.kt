package mx.edu.utng.utngrunner.presentation.game

import mx.edu.utng.utngrunner.domain.model.Coin
import mx.edu.utng.utngrunner.domain.model.GamePhase
import mx.edu.utng.utngrunner.domain.model.GameState
import mx.edu.utng.utngrunner.domain.model.Obstacle
import mx.edu.utng.utngrunner.domain.model.ObstacleType
import mx.edu.utng.utngrunner.domain.model.Player
import kotlin.collections.map

object GameEngine {

    fun update(state: GameState, frame: Long): GameState {
        if (state.phase != GamePhase.PLAYING) return state

        // 1. Actualizar jugador (física)
        val updatedPlayer = updatePlayer(state.player)

        // 2. Calcular puntuación y nivel
        val newScore = state.score + 1
        val newLevel = calculateLevel(newScore)
        val newSpeed = 4f + (newLevel - 1) * 0.8f

        // 3. Actualizar obstáculos y monedas
        val updatedObs   = updateObstacles(state.obstacles, newSpeed, frame)
        val updatedCoins = updateCoins(state.coins, newSpeed, frame)

        // 4. Detectar colisiones
        val afterCollision = checkCollisions(
            updatedPlayer, updatedObs, updatedCoins, state.lives
        )

        return state.copy(
            player    = afterCollision.player,
            score     = newScore,
            level     = newLevel,
            lives     = afterCollision.lives,
            gameSpeed = newSpeed,
            obstacles = afterCollision.obstacles,
            coins     = afterCollision.coins,
            phase     = if (afterCollision.lives <= 0) GamePhase.DEAD
            else GamePhase.PLAYING
        )
    }

    // ── Física del jugador ──────────────────────────────────────────
    private fun updatePlayer(p: Player): Player {
        val newVelY = p.velocityY + Player.GRAVITY
        val newY    = (p.y + newVelY).coerceAtMost(Player.FLOOR_Y)
        val landed  = newY >= Player.FLOOR_Y
        return p.copy(
            y                = newY,
            velocityY        = if (landed) 0f else newVelY,
            isJumping        = !landed && p.isJumping,
            isSliding        = p.slideFrames > 0,
            slideFrames      = (p.slideFrames - 1).coerceAtLeast(0),
            isInvincible     = p.invincibleFrames > 0,
            invincibleFrames = (p.invincibleFrames - 1).coerceAtLeast(0)
        )
    }

    // ── Nivel ───────────────────────────────────────────────────────
    private fun calculateLevel(score: Int): Int =
        (1 + score / 300).coerceAtMost(5)

    // ── Obstáculos ──────────────────────────────────────────────────
    private fun updateObstacles(
        obstacles: List<Obstacle>, speed: Float, frame: Long
    ): List<Obstacle> {
        val moved = obstacles
            .map { it.copy(x = it.x - speed) }
            .filter { it.x > -50f }

        return if (frame % 60 == 0L && Math.random() < 0.6) {
            val type = ObstacleType.values().random()
            moved + Obstacle(
                x = 240f, width = type.w,
                height = type.h, type = type
            )
        } else moved
    }

    // ── Monedas ─────────────────────────────────────────────────────
    private fun updateCoins(
        coins: List<Coin>, speed: Float, frame: Long
    ): List<Coin> {
        val moved = coins
            .map { it.copy(x = it.x - speed) }
            .filter { it.x > -20f && !it.collected }

        return if (frame % 90 == 0L && Math.random() < 0.4) {
            moved + Coin(x = 240f, y = Player.FLOOR_Y - 50f)
        } else moved
    }

    // ── Colisiones AABB ─────────────────────────────────────────────
    private fun checkCollisions(
        player: Player,
        obstacles: List<Obstacle>,
        coins: List<Coin>,
        currentLives: Int
    ): CollisionResult {
        val floor  = Player.FLOOR_Y + 20f
        val cLeft  = player.x - 10f
        val cRight = player.x + 18f
        val cTop   = player.y - (if (player.isSliding) 8f else 30f)
        val cBot   = player.y + 20f

        // Obstáculos golpeados
        val hitObs = obstacles.filter { o ->
            !player.isInvincible &&
                    cRight > o.x + 4f &&
                    cLeft  < o.x + o.width - 4f &&
                    cBot   > floor - o.height &&
                    cTop   < floor
        }

        // Monedas recogidas
        val updatedCoins = coins.map { cn ->
            if (!cn.collected &&
                Math.hypot(
                    (player.x - cn.x).toDouble(),
                    (player.y - cn.y).toDouble()
                ) < 22.0
            ) cn.copy(collected = true) else cn
        }

        return CollisionResult(
            player    = if (hitObs.isNotEmpty())
                player.copy(invincibleFrames = Player.INVINCIBLE_FRAMES)
            else player,
            lives     = (currentLives - hitObs.size).coerceAtLeast(0),
            obstacles = obstacles.map {
                if (it in hitObs) it.copy(x = -999f) else it
            },
            coins     = updatedCoins
        )
    }
}

// ── Resultado de colisión ────────────────────────────────────────────
data class CollisionResult(
    val player: Player,
    val lives: Int,
    val obstacles: List<Obstacle>,
    val coins: List<Coin>
)