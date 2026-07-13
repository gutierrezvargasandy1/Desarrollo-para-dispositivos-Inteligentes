package mx.edu.utng.tv.domain.model

data class LecturaFC(
    val id     : Long   = 0L,
    val bpm    : Int,
    val estado : String, // "Normal", "Elevada", "Bradicardia", etc.
    val hora   : String, // p.ej. "14:32"
)