package mx.edu.utng.wearos.watchface

import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository

class SmartHealthWatchFaceService : WatchFaceService() {

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {

        val renderer = SmartHealthRenderer(
            context                    = applicationContext,
            surfaceHolder              = surfaceHolder,
            watchState                 = watchState,
            currentUserStyleRepository = currentUserStyleRepository
        )

        return WatchFace(WatchFaceType.DIGITAL, renderer)
    }
}