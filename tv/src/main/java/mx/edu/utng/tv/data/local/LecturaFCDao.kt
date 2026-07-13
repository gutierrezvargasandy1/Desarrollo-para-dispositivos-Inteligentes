package mx.edu.utng.tv.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LecturaFCDao {
    @Query("SELECT * FROM lecturas_fc ORDER BY timestamp DESC")
    fun obtenerTodas(): Flow<List<LecturaFCEntity>>

    @Query("SELECT * FROM lecturas_fc ORDER BY timestamp DESC LIMIT 1")
    fun obtenerUltima(): Flow<LecturaFCEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(lectura: LecturaFCEntity)

    @Query("DELETE FROM lecturas_fc")
    suspend fun borrarTodas()
}