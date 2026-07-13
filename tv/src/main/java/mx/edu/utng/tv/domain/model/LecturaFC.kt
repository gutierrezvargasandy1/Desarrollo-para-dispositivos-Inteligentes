package mx.edu.utng.tv.domain.model

data class LecturaFC(
    val id: Int = 0,  // Cambiado de Long a Int
    val bpm: Int,
    val estado: String,
    val hora: String
)