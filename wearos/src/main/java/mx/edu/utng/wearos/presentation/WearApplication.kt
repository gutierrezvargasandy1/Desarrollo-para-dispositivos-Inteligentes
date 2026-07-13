package mx.edu.utng.wearos.presentation

import android.app.Application
import android.util.Log
import mx.edu.utng.wearos.data.SmartHealthRepository

class WearApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("WearApplication", "=== 🚀 WearApplication onCreate() ===")

        try {
            SmartHealthRepository.init(applicationContext)
            Log.d("WearApplication", "✅ SmartHealthRepository inicializado correctamente")
        } catch (e: Exception) {
            Log.e("WearApplication", "❌ Error inicializando SmartHealthRepository", e)
        }
    }
}