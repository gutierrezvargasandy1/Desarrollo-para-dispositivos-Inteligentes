package mx.edu.utng.utngrunner.domain.model

data class GameState(
    val phase: GamePhase = GamePhase.IDLE,
    val player: Player = Player(),
    val obstacles: List<Obstacle> = emptyList(),
    val coins: List<Coin> = emptyList(),
    val score: Int = 0,
    val highScore: Int = 0,
    val lives: Int = 3,
    val level: Int = 1,
    val gameSpeed: Float = 4f,
    val heartRate: Int = 72
)

enum class GamePhase { IDLE, PLAYING, DEAD }