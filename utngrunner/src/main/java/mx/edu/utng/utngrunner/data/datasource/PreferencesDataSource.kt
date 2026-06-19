package mx.edu.utng.utngrunner.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "utng_prefs")

class PreferencesDataSource(private val context: Context) {

    private val HIGH_SCORE_KEY = intPreferencesKey("high_score")

    suspend fun getHighScore(): Int {
        return context.dataStore.data.first()[HIGH_SCORE_KEY] ?: 0
    }

    suspend fun saveHighScore(score: Int) {
        context.dataStore.edit { prefs ->
            prefs[HIGH_SCORE_KEY] = score
        }
    }
}