package mx.edu.utng.utngrunner.domain.repository

interface ScoreRepository {
    suspend fun getHighScore(): Int
    suspend fun saveHighScore(score: Int)
}