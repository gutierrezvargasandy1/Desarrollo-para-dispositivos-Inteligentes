class SmartHealthApplication : Application() {
    lateinit var mqttService: MqttAppService

    override fun onCreate() {
        super.onCreate()
        mqttService = MqttAppService(
            context = this,
            fcFlow = SmartHealthRepository.fcFlow
        )
        mqttService.connect()
    }
}