package mx.edu.utng.memorymatch.domain.repository

import mx.edu.utng.memorymatch.data.datasource.BestTimeDataSource


class BestTimeRepositoryImpl(
    private val ds: BestTimeDataSource
) : BestTimeRepository {
    override suspend fun getBestTime() = ds.getBestTime()
    override suspend fun saveBestTime(s: Long) = ds.saveBestTime(s)
}