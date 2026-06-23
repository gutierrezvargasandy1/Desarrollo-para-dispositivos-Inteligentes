package mx.edu.utng.memorymatch.domain.model

enum class CardSymbol(
    val emoji: String,
    val label: String,
    val color: Long
) {
    STATE_FLOW ("⚡", "StateFlow", 0xFF1565C0),
    VIEW_MODEL ("🏛", "ViewModel", 0xFF1B5E20),
    ROOM       ("🗄", "Room",      0xFF4A148C),
    FLOW       ("🔄", "Flow",      0xFF006064),
    COMPOSE    ("🎨", "Compose",   0xFFE65100),
    DATA_LAYER ("🔗", "DataLayer", 0xFFB71C1C),
}