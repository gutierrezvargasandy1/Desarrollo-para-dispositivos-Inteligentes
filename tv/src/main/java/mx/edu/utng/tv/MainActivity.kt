// mx/edu/utng/tv/MainActivity.kt
package mx.edu.utng.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import mx.edu.utng.tv.ui.TvCatalogScreen
import mx.edu.utng.tv.ui.TvDetailScreen
import mx.edu.utng.tv.ui.TvPlaybackScreen
import mx.edu.utng.tv.ui.theme.SmartHealthMonitorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHealthMonitorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "catalog") {
                        composable("catalog") {
                            TvCatalogScreen(
                                onCardClick = { lecturaId ->
                                    navController.navigate("detail/$lecturaId")
                                }
                            )
                        }
                        composable(
                            route = "detail/{lecturaId}",
                            arguments = listOf(
                                navArgument("lecturaId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("lecturaId") ?: return@composable
                            TvDetailScreen(
                                lecturaId = id,
                                navController = navController
                            )
                        }
                        composable("playback") {
                            TvPlaybackScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}