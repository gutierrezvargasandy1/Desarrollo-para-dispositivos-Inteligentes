import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.mediarouter.app.MediaRouteButton
import com.google.android.gms.cast.framework.CastButtonFactory

@Composable
fun DashboardTopBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            // CastButton: AndroidView que envuelve MediaRouteButton
            AndroidView(
                factory = { context ->
                    MediaRouteButton(context).apply {
                        CastButtonFactory.setUpMediaRouteButton(context, this)
                    }
                },
                modifier = Modifier.size(48.dp)
            )
        }
    )
}