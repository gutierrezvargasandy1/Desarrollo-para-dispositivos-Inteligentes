// mx/edu/utng/tv/ui/TvViewModel.kt
package mx.edu.utng.tv.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mx.edu.utng.tv.domain.model.LecturaFC
import mx.edu.utng.tv.domain.model.TvUiState
import mx.edu.utng.tv.domain.repository.SmartHealthRepository

class TvViewModel(
    private val repository: SmartHealthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TvUiState())
    val state: StateFlow<TvUiState> = _state.asStateFlow()

    init {
        // Cargar datos de prueba iniciales
        val lecturasDemo = listOf(
            LecturaFC(id = 1, bpm = 72, estado = "Normal", hora = "10:00"),
            LecturaFC(id = 2, bpm = 85, estado = "Normal", hora = "10:05"),
            LecturaFC(id = 3, bpm = 110, estado = "Elevada", hora = "10:10"),
            LecturaFC(id = 4, bpm = 75, estado = "Normal", hora = "10:15")
        )
        _state.value = TvUiState(lecturas = lecturasDemo, isLoading = false)

        // Si quieres cargar desde Room cuando esté disponible:
        /*
        viewModelScope.launch {
            repository.obtenerHistorial()
                .catch { e -> _state.update { it.copy(error = e.message, isLoading = false) } }
                .collect { lecturas ->
                    _state.update {
                        it.copy(
                            lecturas = if (lecturas.isNotEmpty()) lecturas else lecturasDemo,
                            isLoading = false
                        )
                    }
                }
        }
        */

        viewModelScope.launch {
            repository.fcActual.collect { bpm ->
                _state.update { it.copy(fcActual = bpm) }
            }
        }
    }
}