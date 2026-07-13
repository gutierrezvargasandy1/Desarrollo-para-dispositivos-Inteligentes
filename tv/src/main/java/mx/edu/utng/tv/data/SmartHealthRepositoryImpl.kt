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
    private val dao: LecturaFCDao // tu DAO de Room de sesiones anteriores
) : SmartHealthRepository {

    // StateFlow compartido: el sensor/servicio actualiza este valor,
    // tanto la app de teléfono como el módulo tv lo observan.
    private val _fcActual = MutableStateFlow(0)
    override val fcActual: StateFlow<Int> = _fcActual

    override fun obtenerHistorial(): Flow<List<LecturaFC>> {
        return dao.obtenerTodas() // Flow<List<LecturaFCEntity>> de tu Room DAO
            .map { entidades -> entidades.map { it.toDomain() } }
    }

    /** Llamar desde donde recibas la lectura del sensor (Bluetooth, mock, etc.) */
    fun actualizarFcActual(bpm: Int) {
        _fcActual.value = bpm
    }
}

// Ajusta este mapper a los campos reales de tu entidad Room
private fun LecturaFCEntity.toDomain() = LecturaFC(
    id = id,
    bpm = bpm,
    estado = estado,
    hora = hora
)