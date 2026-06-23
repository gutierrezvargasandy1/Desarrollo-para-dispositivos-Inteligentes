package mx.edu.utng.memorymatch.domain.model

data class GameState(
    val board: List<Card>     = emptyList(),
    val phase: GamePhase      = GamePhase.IDLE,
    val firstSelected: Int?   = null,
    val secondSelected: Int?  = null,
    val matchesFound: Int     = 0,
    val moves: Int            = 0,
    val elapsedSeconds: Long  = 0L,
    val bestTime: Long        = Long.MAX_VALUE,
) {
    val isComplete: Boolean get() = matchesFound == TOTAL_PAIRS

    companion object {
        const val TOTAL_PAIRS = 6
        const val TOTAL_CARDS = TOTAL_PAIRS * 2
    }
}

enum class GamePhase {
    IDLE,
    SELECTING_FIRST,
    WAITING_SECOND,
    CHECKING,
    WON
}