package mx.edu.utng.wearos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import mx.edu.utng.wearos.data.SmartHealthRepository
import mx.edu.utng.wearos.data.db.LecturaFC

class WearDashboardViewModel : ViewModel() {

    private val _fc = MutableStateFlow(120)
    val fc: StateFlow<Int> = _fc

    fun actualizarFC(valor: Int) {
        _fc.value = valor
    }

    // NUEVO: historial desde Room
    val historial: StateFlow<List<LecturaFC>> =
        SmartHealthRepository.obtenerHistorial()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()

            )
    private val mqttPublisher = MqttWearPublisher(application)

    init {
        mqttPublisher.connect()
        viewModelScope.launch {
            heartRateSource.heartRate.collect { bpm ->
                _state.update { it.copy(fcActual = bpm) }
                val estado = when {
                    bpm < 60 -> "FC Baja"
                    bpm > 100 -> "FC Alta"
                    else -> "Normal"
                }
                mqttPublisher.publishFC(bpm, estado)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mqttPublisher.disconnect()
    }
}