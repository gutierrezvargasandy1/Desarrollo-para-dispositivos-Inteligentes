package mx.edu.utng.tv.domain.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utng.tv.domain.model.LecturaFC

interface SmartHealthRepository {

    /** Historial completo de lecturas, reactivo desde Room DAO. */
    fun obtenerHistorial(): Flow<List<LecturaFC>>

    /** FC actual del sensor — StateFlow compartido entre app y tv. */
    val fcActual: StateFlow<Int>
}