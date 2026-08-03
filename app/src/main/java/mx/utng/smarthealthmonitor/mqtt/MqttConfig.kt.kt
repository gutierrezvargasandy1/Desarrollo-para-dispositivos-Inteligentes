package mx.utng.smarthealthmonitor.mqtt

object `MqttConfig.kt` {

    const val BROKER_URL = "de9f578bab454018b5ecd9e047efdc73.s1.eu.hivemq.cloud:8883"
    const val USERNAME = "barrientosana"
    const val PASSWORD = "linux123"

    const val TOPIC_FC = "utng/smarthealthmonitor/fc"
    const val TOPIC_TV = "utng/smarthealthmonitor/tv"
    const val TOPIC_ALERT = "utng/smarthealthmonitor/alerta"

    const val QOS = 1

    const val CLIENT_WEAR = "smarthealthmonitor-wear"
    const val CLIENT_APP = "smarthealthmonitor-app"
    const val CLIENT_TV = "smarthealthmonitor-tv"
}