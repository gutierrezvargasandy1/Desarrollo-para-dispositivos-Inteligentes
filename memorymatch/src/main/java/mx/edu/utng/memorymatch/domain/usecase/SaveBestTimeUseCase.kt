package mx.edu.utng.memorymatch.domain.usecase

import mx.edu.utng.memorymatch.domain.repository.BestTimeRepository


class SaveBestTimeUseCase(private val repository: BestTimeRepository) {
    suspend operator fun invoke(seconds: Long) =
        repository.saveBestTime(seconds)
}