package mx.edu.utng.memorymatch.domain.model

data class Card(
    val id: Int,
    val symbol: CardSymbol,
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false,
)