package mx.edu.utng.memorymatch.domain.usecase

import mx.edu.utng.memorymatch.domain.repository.BestTimeRepository


class GetBestTimeUseCase(private val repository: BestTimeRepository) {
    suspend operator fun invoke(): Long =
        repository.getBestTime()
}