package mx.edu.utng.memorymatch.domain.repository

interface BestTimeRepository {
    suspend fun getBestTime(): Long
    suspend fun saveBestTime(seconds: Long)
}