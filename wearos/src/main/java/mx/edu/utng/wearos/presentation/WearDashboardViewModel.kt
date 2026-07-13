package mx.edu.utng.wearos.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.edu.utng.wearos.data.SmartHealthRepository
import mx.edu.utng.wearos.data.db.LecturaFC

class WearDashboardViewModel : ViewModel() {

    private val _fc = MutableStateFlow(0)
    val fc: StateFlow<Int> = _fc

    // Historial desde Room
    val historial: StateFlow<List<LecturaFC>> =
        SmartHealthRepository.obtenerHistorial()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    /**
     * Actualiza la FC y GUARDA en la base de datos
     */
    fun actualizarYGuardarFC(valor: Int) {
        Log.d("WearViewModel", "=== actualizarYGuardarFC llamado con: $valor ===")

        // Actualizar el StateFlow
        _fc.value = valor

        // Guardar en Room usando el repositorio
        try {
            SmartHealthRepository.actualizarFC(valor)
            Log.d("WearViewModel", "✅ Lectura guardada correctamente: $valor bpm")
        } catch (e: Exception) {
            Log.e("WearViewModel", "❌ Error guardando lectura", e)
        }
    }

    /**
     * Solo actualiza la FC sin guardar (para UI)
     */
    fun actualizarFC(valor: Int) {
        _fc.value = valor
    }
}