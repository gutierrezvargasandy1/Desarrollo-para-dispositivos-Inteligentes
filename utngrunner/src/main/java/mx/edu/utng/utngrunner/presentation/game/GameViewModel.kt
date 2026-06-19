package mx.edu.utng.utngrunner.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.edu.utng.utngrunner.data.datasource.HeartRateDataSource
import mx.edu.utng.utngrunner.domain.model.GamePhase
import mx.edu.utng.utngrunner.domain.model.GameState
import mx.edu.utng.utngrunner.domain.model.Player
import mx.edu.utng.utngrunner.domain.usecase.GetHighScoreUseCase
import mx.edu.utng.utngrunner.domain.usecase.SaveHighScoreUseCase

enum class HapticType { JUMP, HIT }

class GameViewModel(
    private val getHighScore: GetHighScoreUseCase,
    private val saveHighScore: SaveHighScoreUseCase,
    private val heartRateSource: HeartRateDataSource
) : ViewModel() {

    // ── Estado de UI (solo lectura para la UI) ───────────────────────
    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    // ── Canal de haptics ─────────────────────────────────────────────
    private val _hapticChannel = Channel<HapticType>(Channel.BUFFERED)
    val hapticEvents = _hapticChannel.receiveAsFlow()

    private var gameJob: Job? = null
    private var frame = 0L

    init {
        loadHighScore()
        observeHeartRate()
    }

    // ── Loop principal 60fps ─────────────────────────────────────────
    private fun startGameLoop() {
        gameJob?.cancel()
        gameJob = viewModelScope.launch {
            while (_state.value.phase == GamePhase.PLAYING) {
                frame++
                _state.update { currentState ->
                    GameEngine.update(currentState, frame)
                }
                delay(16L) // ~60fps
            }
            // Guardar puntuación al morir
            saveHighScore(_state.value.score)
        }
    }

    // ── Controles del jugador ────────────────────────────────────────
    fun onJump() {
        when (_state.value.phase) {

            GamePhase.IDLE -> {
                // Iniciar juego
                frame = 0L
                _state.update { GameState(phase = GamePhase.PLAYING) }
                startGameLoop()
            }

            GamePhase.DEAD -> {
                // Reiniciar juego
                frame = 0L
                _state.update {
                    GameState(
                        phase     = GamePhase.PLAYING,
                        highScore = it.highScore
                    )
                }
                startGameLoop()
            }

            GamePhase.PLAYING -> {
                // Saltar si está en el suelo
                if (!_state.value.player.isJumping) {
                    _state.update {
                        it.copy(
                            player = it.player.copy(
                                velocityY = Player.JUMP_VELOCITY,
                                isJumping = true
                            )
                        )
                    }
                    _hapticChannel.trySend(HapticType.JUMP)
                }
            }
        }
    }

    fun onSlide() {
        if (_state.value.phase != GamePhase.PLAYING) return
        if (_state.value.player.isJumping) return

        _state.update {
            it.copy(
                player = it.player.copy(
                    slideFrames = Player.SLIDE_DURATION
                )
            )
        }
    }

    // ── Cargar mejor puntuación ──────────────────────────────────────
    private fun loadHighScore() {
        viewModelScope.launch {
            val hs = getHighScore()
            _state.update { it.copy(highScore = hs) }
        }
    }

    // ── Observar frecuencia cardíaca ─────────────────────────────────
    private fun observeHeartRate() {
        viewModelScope.launch {
            heartRateSource.heartRate.collect { bpm ->
                _state.update { it.copy(heartRate = bpm) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameJob?.cancel()
    }
}