package mx.edu.utng.tv.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import mx.edu.utng.tv.data.local.LecturaFCDao
import mx.edu.utng.tv.data.local.LecturaFCEntity
import mx.edu.utng.tv.domain.model.LecturaFC
import mx.edu.utng.tv.domain.repository.SmartHealthRepository

class SmartHealthRepositoryImpl(
    private val dao: LecturaFCDao
) : SmartHealthRepository {

    private val _fcActual = MutableStateFlow(0)
    override val fcActual: StateFlow<Int> = _fcActual

    override fun obtenerHistorial(): Flow<List<LecturaFC>> {
        return dao.obtenerTodas()
            .map { entidades -> entidades.map { it.toDomain() } }
    }

    fun actualizarFcActual(bpm: Int) {
        _fcActual.value = bpm
    }
}

private fun LecturaFCEntity.toDomain() = LecturaFC(
    id = id.toInt(),
    bpm = bpm,
    estado = estado,
    hora = hora
)