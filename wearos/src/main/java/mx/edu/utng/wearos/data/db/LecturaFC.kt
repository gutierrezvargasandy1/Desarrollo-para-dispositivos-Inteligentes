package mx.edu.utng.wearos.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "lecturas_fc")
data class LecturaFC(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val valorBpm: Int,
    val hora: String = LocalTime.now()
        .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
    val esNormal: Boolean = valorBpm in 60..100
)