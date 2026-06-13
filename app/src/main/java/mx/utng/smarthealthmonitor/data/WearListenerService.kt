package mx.utng.smarthealthmonitor.data

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class WearListenerService : WearableListenerService() {

    companion object {
        const val PATH_FC = "/smarthealthmonitor/fc"
        const val PATH_PASOS = "/smarthealthmonitor/pasos"

        private const val TAG = "WearListener"
        private const val PHONE_TEST = "PHONE_TEST"
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {

        Log.d(
            PHONE_TEST,
            "Mensaje recibido path=${messageEvent.path}"
        )

        val data = String(messageEvent.data)
        val path = messageEvent.path

        Log.d(
            PHONE_TEST,
            "Dato recibido=$data"
        )

        Log.d(
            TAG,
            "Mensaje recibido: path=$path, data=$data"
        )

        when (path) {

            PATH_FC -> {

                val bpm = data.toIntOrNull() ?: return

                Log.d(
                    PHONE_TEST,
                    "FC recibida=$bpm"
                )

                SmartHealthRepository.actualizarFC(bpm)
            }

            PATH_PASOS -> {

                val pasos = data.toIntOrNull() ?: return

                Log.d(
                    PHONE_TEST,
                    "Pasos recibidos=$pasos"
                )

                SmartHealthRepository.actualizarPasos(pasos)
            }

            else -> {

                Log.w(
                    PHONE_TEST,
                    "Path desconocido: $path"
                )

                Log.w(
                    TAG,
                    "Path desconocido: $path"
                )
            }
        }
    }
}