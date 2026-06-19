package mx.edu.utng.utngrunner.presentation.game

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.health.services.client.HealthServices
import mx.edu.utng.utngrunner.data.datasource.HeartRateDataSource
import mx.edu.utng.utngrunner.data.datasource.PreferencesDataSource
import mx.edu.utng.utngrunner.data.repository.ScoreRepositoryImpl
import mx.edu.utng.utngrunner.domain.usecase.GetHighScoreUseCase
import mx.edu.utng.utngrunner.domain.usecase.SaveHighScoreUseCase


class GameViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Construir dependencias manualmente (DI manual)
        val dataSource      = PreferencesDataSource(context)
        val repository      = ScoreRepositoryImpl(dataSource)
        val getHighScore    = GetHighScoreUseCase(repository)
        val saveHighScore   = SaveHighScoreUseCase(repository)
        val healthClient    = HealthServices.getClient(context)
        val heartRateSource = HeartRateDataSource(healthClient)

        @Suppress("UNCHECKED_CAST")
        return GameViewModel(
            getHighScore    = getHighScore,
            saveHighScore   = saveHighScore,
            heartRateSource = heartRateSource
        ) as T
    }
}