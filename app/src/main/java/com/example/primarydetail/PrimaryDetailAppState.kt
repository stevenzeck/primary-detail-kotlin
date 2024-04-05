package com.example.primarydetail

import android.content.res.Resources
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.primarydetail.MainDestinations.POSTS_LIST_ROUTE
import com.example.primarydetail.util.TopBarState

@ExperimentalMaterial3Api
class PrimaryDetailAppState(
    val navController: NavHostController,
    val resources: Resources,
) {
    var topBarState: TopBarState by mutableStateOf(TopBarState())

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToSettings() {
        navController.navigate(MainDestinations.SETTINGS_ROUTE)
    }

    private val currentRoute: State<NavBackStackEntry?>
        @Composable get() = navController
            .currentBackStackEntryAsState()

    val showBackButton: Boolean
        @Composable get() = !arrayOf(
            POSTS_LIST_ROUTE,
        ).contains(currentRoute.value?.destination?.route)
}

@ExperimentalMaterial3Api
@Composable
fun rememberPrimaryDetailState(
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
) = remember(navController, resources) {
    PrimaryDetailAppState(navController, resources)
}

@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
