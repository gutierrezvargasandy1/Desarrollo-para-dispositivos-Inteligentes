package mx.edu.utng.memorymatch.domain.usecase

import mx.edu.utng.memorymatch.domain.model.Card
import mx.edu.utng.memorymatch.domain.model.CardSymbol


class ShuffleBoardUseCase {
    operator fun invoke(): List<Card> =
        CardSymbol.values()
            .flatMap { symbol -> listOf(symbol, symbol) }
            .shuffled()
            .mapIndexed { index, symbol ->
                Card(id = index, symbol = symbol)
            }
}