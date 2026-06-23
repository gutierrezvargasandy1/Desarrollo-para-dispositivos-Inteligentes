package mx.edu.utng.memorymatch.domain.usecase

import mx.edu.utng.memorymatch.domain.model.GameState


class CheckMatchUseCase {
    operator fun invoke(state: GameState): MatchResult {
        val first  = state.firstSelected  ?: return MatchResult.PENDING
        val second = state.secondSelected ?: return MatchResult.PENDING

        val cardA = state.board[first]
        val cardB = state.board[second]

        return if (cardA.symbol == cardB.symbol) MatchResult.HIT
        else MatchResult.MISS
    }
}

enum class MatchResult { HIT, MISS, PENDING }