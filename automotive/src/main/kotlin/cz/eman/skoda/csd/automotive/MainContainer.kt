package cz.eman.skoda.csd.automotive

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.eman.skoda.csd.automotive.feature.dashboard.presentation.view.DashboardScreen
import cz.eman.skoda.csd.automotive.feature.service.presentation.view.ServicesScreen

@Composable
fun App(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val activity = LocalContext.current as Activity
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "/",
    ) {
        composable(route = "/") {
            DashboardScreen(
                onBackClick = activity::onBackPressed,
                onDiscoverClick = {
                    navController.navigate("services")
                },
            )
        }

        composable(route = "services") {
            ServicesScreen(
                onBackClick = navController::navigateUp,
                onServiceClick = { id ->

                },
            )
        }
    }
}
