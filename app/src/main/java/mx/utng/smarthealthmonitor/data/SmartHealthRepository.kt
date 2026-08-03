package mx.utng.smarthealthmonitor.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import mx.utng.smarthealthmonitor.data.db.LecturaFC
import mx.utng.smarthealthmonitor.data.db.LecturaFCDao
import mx.utng.smarthealthmonitor.data.db.SmartHealthDB

object SmartHealthRepository {

    private val _fcFlow = MutableStateFlow(0)
    val fcFlow: StateFlow<Int> = _fcFlow.asStateFlow()

    private val _pasosFlow = MutableStateFlow(0)
    val pasosFlow: StateFlow<Int> = _pasosFlow.asStateFlow()

    private var dao: LecturaFCDao? = null

    fun init(context: Context) {
        dao = SmartHealthDB
            .getDatabase(context)
            .lecturaDao()
    }

    fun actualizarFC(
        bpm: Int
    ) {

        _fcFlow.value = bpm

        dao?.let { dao ->

            CoroutineScope(Dispatchers.IO).launch {

                dao.insertar(
                    LecturaFC(
                        valorBpm = bpm
                    )
                )
            }
        }
    }

    fun actualizarPasos(
        pasos: Int
    ) {
        _pasosFlow.value = pasos
    }

    fun obtenerHistorial():
            Flow<List<LecturaFC>> {

        return dao?.obtenerUltimas()
            ?: emptyFlow()
    }
}