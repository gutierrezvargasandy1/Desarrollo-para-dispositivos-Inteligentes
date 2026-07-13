package mx.edu.utng.wearos.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import mx.edu.utng.wearos.data.db.LecturaFC
import mx.edu.utng.wearos.data.db.LecturaFCDao
import mx.edu.utng.wearos.data.db.SmartHealthDB

object SmartHealthRepository {

    private val _fcFlow = MutableStateFlow(0)
    val fcFlow: StateFlow<Int> = _fcFlow.asStateFlow()

    private var dao: LecturaFCDao? = null
    private var isInitialized = false

    // ✅ Auto-inicialización: se ejecuta cuando se usa el objeto
    private fun ensureInitialized(context: Context) {
        if (!isInitialized) {
            synchronized(this) {
                if (!isInitialized) {
                    init(context)
                }
            }
        }
    }

    fun init(context: Context) {
        Log.d("SmartHealthRepo", "=== 🔧 init() llamado ===")

        try {
            Log.d("SmartHealthRepo", "📁 Creando base de datos...")
            val db = SmartHealthDB.getDatabase(context)
            dao = db.lecturaDao()
            isInitialized = true
            Log.d("SmartHealthRepo", "✅ Repositorio inicializado CORRECTAMENTE")
            Log.d("SmartHealthRepo", "📊 DAO: ${dao?.javaClass?.simpleName}")
        } catch (e: Exception) {
            Log.e("SmartHealthRepo", "❌ Error inicializando repositorio", e)
            isInitialized = false
        }
    }

    fun actualizarFC(bpm: Int) {
        Log.d("SmartHealthRepo", "=== 📝 actualizarFC($bpm) ===")

        if (!isInitialized || dao == null) {
            Log.e("SmartHealthRepo", "❌ ERROR: Repositorio NO inicializado!")
            Log.e("SmartHealthRepo", "   isInitialized: $isInitialized")
            Log.e("SmartHealthRepo", "   dao is null: ${dao == null}")
            Log.e("SmartHealthRepo", "   ⚠️ ¿Se llamó a SmartHealthRepository.init()?")
            return
        }

        _fcFlow.value = bpm

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val lectura = LecturaFC(valorBpm = bpm)
                dao?.insertar(lectura)

                val count = dao?.contarLecturas() ?: 0
                Log.d("SmartHealthRepo", "✅ Lectura guardada: $bpm bpm")
                Log.d("SmartHealthRepo", "📊 Total lecturas: $count")
            } catch (e: Exception) {
                Log.e("SmartHealthRepo", "❌ Error guardando lectura", e)
            }
        }
    }

    fun obtenerHistorial(): Flow<List<LecturaFC>> {
        Log.d("SmartHealthRepo", "📋 obtenerHistorial()")
        Log.d("SmartHealthRepo", "   isInitialized: $isInitialized")
        Log.d("SmartHealthRepo", "   dao is null: ${dao == null}")

        if (!isInitialized || dao == null) {
            Log.w("SmartHealthRepo", "⚠️ DAO no inicializado, retornando Flow vacío")
            return emptyFlow()
        }

        return dao?.obtenerUltimas() ?: emptyFlow()
    }

    fun isInitialized(): Boolean = isInitialized

    suspend fun borrarTodas() {
        try {
            dao?.borrarTodas()
            Log.d("SmartHealthRepo", "🗑️ Todas las lecturas borradas")
        } catch (e: Exception) {
            Log.e("SmartHealthRepo", "Error borrando lecturas", e)
        }
    }
}