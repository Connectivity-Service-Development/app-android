package cz.eman.skoda.csd.mobile.feature.dashboard.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.eman.skoda.csd.mobile.feature.service.presentation.view.ServicesScreen

@Composable
fun DashboardContainer(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "/",
    ) {
        composable("/") {
            DashboardScreen(
                onServicesClick = {
                    navController.navigate("services")
                },
            )
        }

        composable("services") {
            ServicesScreen(
                onBackClick = navController::navigateUp,
            )
        }
    }
}
