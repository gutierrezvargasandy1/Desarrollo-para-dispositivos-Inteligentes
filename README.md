# SmartHealth Monitor

![Android CI](https://img.shields.io/badge/Android-API26+-green)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-MD3-blue)

Aplicación Android de monitoreo de salud personal en tiempo real. Desarrollada como proyecto integrador — UTNG 9° Cuatrimestre.

## Stack tecnológico

| Tecnología | Uso |
| :--- | :--- |
| **Kotlin + Jetpack Compose** | UI declarativa con Material Design 3 |
| **Wearable Data Layer API** | Comunicación reloj ↔ teléfono (BLE) |
| **Health Services API** | Sensor FC real en background (Wear OS) |
| **Room Database** | Historial persistente de lecturas FC |
| **Jetpack Navigation** | NavHost entre las pantallas de la aplicación |
| **GitHub + Conventional Commits** | Control de versiones profesional |

## Pantallas

| Pantalla | Descripción |
| :--- | :--- |
| **LoginScreen** | Autenticación con validación de campos y estados de carga |
| **DashboardScreen** | Visualización de FC y Pasos en tiempo real provenientes del wearable con botón de pánico |
| **HistorialScreen** | Listado de lecturas persistidas en Room DB mediante el uso de Flow reactivo |
| **AlertaScreen** | Diálogo emergente (AlertDialog MD3) con spinner de carga y Snackbar de confirmación |
## Unidad II — Wear OS
| Pantalla | Descripción |
|---|---|
| WearDashboardScreen | FC en tiempo real con ScalingLazyColumn y TimeText |
| WearHistorialScreen | Lista con Rotary Input (corona del reloj) |
| WearAlertaScreen | Botones circulares de confirmación |
| SmartHealth WatchFace | Hora + FC en el WatchFace nativo |

### WatchFace

![WatchFace](screenshots/watchface.png)

### WearDashboard

![WearDashboard](screenshots/wear_dashboard.png)


## Capturas de pantalla

Región de evidencias visuales del comportamiento de la aplicación en el emulador:

### Login
![Login](screenshots/login.png)

### Dashboard
![Dashboard](screenshots/dashboard.png)

### Historial
![Historial](screenshots/historial.png)

### Alerta de Emergencia
![Alerta](screenshots/alerta.png)

# 📦 MemoryMatchWear OS — Estado del Repositorio

- 🧠 Descripción del juego Memory Match para Wear OS
- 🏗️ Arquitectura Clean + MVVM
- ⚙️ Stack técnico (Compose, StateFlow, Room/DataStore, Coroutines, Haptics)
- 🎮 Mecánica del juego (IDLE → SELECTING → CHECKING → WON)
- 📁 Estructura del proyecto
- 🚀 Instrucciones de ejecución (`./gradlew assemble`, `./gradlew test`)
- 🧪 Cómo correr pruebas unitarias
- 🏷️ Información de versión `v1.0.0`

## 🏷️ Tag del repositorio

## Autor

Jose Andres Gutierrez Vargas — UTNG — Ing. en Desarrollo y Gestión de Software (GIDS6093)
