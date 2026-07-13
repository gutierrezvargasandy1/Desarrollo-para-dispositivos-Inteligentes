package mx.edu.utng.tv.data

import android.content.Context
import mx.edu.utng.tv.data.local.AppDatabase
import mx.edu.utng.tv.domain.repository.SmartHealthRepository

object ServiceLocator {
    @Volatile
    private var repository: SmartHealthRepository? = null

    fun provideRepository(context: Context): SmartHealthRepository {
        return repository ?: synchronized(this) {
            repository ?: buildRepository(context).also { repository = it }
        }
    }

    private fun buildRepository(context: Context): SmartHealthRepository {
        val db = AppDatabase.getInstance(context)
        return SmartHealthRepositoryImpl(dao = db.lecturaFCDao())
    }
}