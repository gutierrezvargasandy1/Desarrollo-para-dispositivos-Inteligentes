package mx.edu.utng.tv.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "lecturas_fc")
data class LecturaFCEntity(
    @PrimaryKey(autoGenerate = true)
    val id     : Long   = 0L,
    val bpm    : Int,
    val estado : String,
    val hora   : String,
    val timestamp: Long = System.currentTimeMillis(), // útil para ordenar por fecha
)