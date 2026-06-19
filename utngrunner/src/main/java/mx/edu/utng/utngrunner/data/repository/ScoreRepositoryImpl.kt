package mx.edu.utng.utngrunner.data.repository

import mx.edu.utng.utngrunner.data.datasource.PreferencesDataSource
import mx.edu.utng.utngrunner.domain.repository.ScoreRepository

class ScoreRepositoryImpl(
    private val dataSource: PreferencesDataSource  // ← debe tener este parámetro
) : ScoreRepository {

    override suspend fun getHighScore(): Int =
        dataSource.getHighScore()

    override suspend fun saveHighScore(score: Int) =
        dataSource.saveHighScore(score)
}