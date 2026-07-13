package mx.edu.utng.tv.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utng.tv.domain.model.LecturaFC

interface SmartHealthRepository {
    fun obtenerHistorial(): Flow<List<LecturaFC>>
    val fcActual: StateFlow<Int>
}