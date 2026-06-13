package mx.edu.utng.wearos

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await

class WearDataSender(
    private val context: Context
) {

    suspend fun enviarFC(bpm: Int) {

        try {

            val nodes = Wearable
                .getNodeClient(context)
                .connectedNodes
                .await()

            Log.d(
                "WEAR_TEST",
                "Nodos encontrados: ${nodes.size}"
            )

            nodes.forEach { node ->

                Log.d(
                    "WEAR_TEST",
                    "Enviando a nodo ${node.id}"
                )

                Wearable
                    .getMessageClient(context)
                    .sendMessage(
                        node.id,
                        "/smarthealthmonitor/fc",
                        bpm.toString().toByteArray()
                    )
                    .await()

                Log.d(
                    "WEAR_TEST",
                    "Mensaje enviado correctamente"
                )
            }

        } catch (e: Exception) {

            Log.e(
                "WEAR_TEST",
                "Error enviando mensaje",
                e
            )
        }
    }
}