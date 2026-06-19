package mx.edu.utng.utngrunner

import junit.framework.TestCase.assertTrue
import mx.edu.utng.utngrunner.domain.model.GamePhase
import mx.edu.utng.utngrunner.domain.model.GameState
import mx.edu.utng.utngrunner.domain.model.Obstacle
import mx.edu.utng.utngrunner.domain.model.ObstacleType
import mx.edu.utng.utngrunner.domain.model.Player
import mx.edu.utng.utngrunner.presentation.game.GameEngine
import org.junit.Test
import kotlin.test.assertEquals


class GameEngineTest {

    // ── Test 1: El juego no avanza si no está en PLAYING ─────────────
    @Test
    fun `game does not update when not playing`() {
        val state = GameState(phase = GamePhase.IDLE)
        val next  = GameEngine.update(state, frame = 0)
        assertEquals(GamePhase.IDLE, next.phase)
        assertEquals(0, next.score)
    }

    // ── Test 2: La puntuación aumenta cada frame ──────────────────────
    @Test
    fun `score increases each frame`() {
        val state = GameState(phase = GamePhase.PLAYING, score = 0)
        val next  = GameEngine.update(state, frame = 1)
        assertEquals(1, next.score)
    }

    // ── Test 3: El nivel sube al llegar a 300 puntos ──────────────────
    @org.junit.Test
    fun `level increases at score 300`() {
        val state = GameState(phase = GamePhase.PLAYING, score = 299)
        val next  = GameEngine.update(state, frame = 1)
        assertEquals(2, next.level)
    }

    // ── Test 4: El nivel no supera 5 ─────────────────────────────────
    @Test
    fun `level does not exceed 5`() {
        val state = GameState(phase = GamePhase.PLAYING, score = 9999)
        val next  = GameEngine.update(state, frame = 1)
        assertEquals(5, next.level)
    }

    // ── Test 5: Las vidas disminuyen al chocar ────────────────────────
    @Test
    fun `lives decrease on obstacle collision`() {
        val obstacle = Obstacle(
            x = Player().x - 5f,
            width = 20,
            height = 35,
            type = ObstacleType.TAREA
        )
        val state = GameState(
            phase     = GamePhase.PLAYING,
            player    = Player(y = Player.FLOOR_Y, isInvincible = false),
            obstacles = listOf(obstacle),
            lives     = 3
        )
        val next = GameEngine.update(state, frame = 1)
        assertTrue(next.lives < 3)
    }

    // ── Test 6: El jugador es invencible tras un golpe ────────────────
    @Test
    fun `player becomes invincible after hit`() {
        val obstacle = Obstacle(
            x      = Player().x - 5f,
            width  = 20,
            height = 35,
            type   = ObstacleType.TAREA
        )
        val state = GameState(
            phase     = GamePhase.PLAYING,
            player    = Player(y = Player.FLOOR_Y, isInvincible = false),
            obstacles = listOf(obstacle),
            lives     = 3
        )
        val next = GameEngine.update(state, frame = 1)
        assertTrue(next.player.invincibleFrames > 0)
    }

    // ── Test 7: Game Over cuando las vidas llegan a 0 ─────────────────
    @Test
    fun `game over when lives reach zero`() {
        val state = GameState(
            phase = GamePhase.PLAYING,
            lives = 0
        )
        val next = GameEngine.update(state, frame = 1)
        assertEquals(GamePhase.DEAD, next.phase)
    }

    // ── Test 8: El jugador cae por gravedad ───────────────────────────
    @Test
    fun `player falls due to gravity when jumping`() {
        val state = GameState(
            phase  = GamePhase.PLAYING,
            player = Player(
                y         = 100f,
                velocityY = 0f,
                isJumping = true
            )
        )
        val next = GameEngine.update(state, frame = 1)
        assertTrue(next.player.velocityY > 0f)
    }

    // ── Test 9: El jugador no cae bajo el piso ────────────────────────
    @Test
    fun `player does not fall below floor`() {
        val state = GameState(
            phase  = GamePhase.PLAYING,
            player = Player(
                y         = Player.FLOOR_Y,
                velocityY = 10f,
                isJumping = false
            )
        )
        val next = GameEngine.update(state, frame = 1)
        assertTrue(next.player.y <= Player.FLOOR_Y)
    }

    // ── Test 10: La velocidad aumenta con el nivel ────────────────────
    @Test
    fun `game speed increases with level`() {
        val stateLevel1 = GameState(phase = GamePhase.PLAYING, score = 0)
        val stateLevel2 = GameState(phase = GamePhase.PLAYING, score = 299)
        val next1 = GameEngine.update(stateLevel1, frame = 1)
        val next2 = GameEngine.update(stateLevel2, frame = 1)
        assertTrue(next2.gameSpeed > next1.gameSpeed)
    }
}