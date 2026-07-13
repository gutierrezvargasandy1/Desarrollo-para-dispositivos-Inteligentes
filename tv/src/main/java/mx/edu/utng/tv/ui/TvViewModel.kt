package mx.edu.utng.tv.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mx.edu.utng.tv.domain.model.TvUiState
import mx.edu.utng.tv.domain.repository.SmartHealthRepository

class TvViewModel(
    private val repository: SmartHealthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TvUiState())
    val state: StateFlow<TvUiState> = _state.asStateFlow()

    init {
        // Observar historial reactivo del Room DAO
        viewModelScope.launch {
            repository.obtenerHistorial()
                .catch { e -> _state.update { it.copy(error = e.message, isLoading = false) } }
                .collect { lecturas ->
                    _state.update { it.copy(lecturas = lecturas, isLoading = false) }
                }
        }
        // Observar FC actual (StateFlow del sensor)
        viewModelScope.launch {
            repository.fcActual.collect { bpm ->
                _state.update { it.copy(fcActual = bpm) }
            }
        }
    }
}