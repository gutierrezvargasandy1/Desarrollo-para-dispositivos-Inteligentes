package mx.edu.utng.memorymatch.presentation.board


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utng.memorymatch.data.datasource.BestTimeDataSource
import mx.edu.utng.memorymatch.domain.repository.BestTimeRepositoryImpl
import mx.edu.utng.memorymatch.domain.usecase.CheckMatchUseCase
import mx.edu.utng.memorymatch.domain.usecase.FlipCardUseCase
import mx.edu.utng.memorymatch.domain.usecase.GetBestTimeUseCase
import mx.edu.utng.memorymatch.domain.usecase.SaveBestTimeUseCase
import mx.edu.utng.memorymatch.domain.usecase.ShuffleBoardUseCase

class MemoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dataSource  = BestTimeDataSource(context)
        val repository  = BestTimeRepositoryImpl(dataSource)
        return MemoryViewModel(
            shuffleBoard = ShuffleBoardUseCase(),
            flipCard     = FlipCardUseCase(),
            checkMatch   = CheckMatchUseCase(),
            saveBestTime = SaveBestTimeUseCase(repository),
            getBestTime  = GetBestTimeUseCase(repository),
        ) as T
    }
}