package com.example.primarydetail

import android.content.res.Resources
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldState
import androidx.compose.material3.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.primarydetail.MainDestinations.POSTS_LIST_ROUTE

@ExperimentalMaterial3Api
class PrimaryDetailAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val resources: Resources,
) {
    var topBarText by mutableStateOf("")

    fun upPress() {
        navController.navigateUp()
    }

    val currentRoute: State<NavBackStackEntry?>
        @Composable get() = navController
            .currentBackStackEntryAsState()

    val shouldShowBackButton: Boolean
        @Composable get() = !arrayOf(
            POSTS_LIST_ROUTE,
        ).contains(currentRoute.value?.destination?.route)

    val inActionMode: Boolean
        @Composable get() = selectedItems > 0

    var selectedItems: Int by mutableStateOf(0)
}

@ExperimentalMaterial3Api
@Composable
fun rememberPrimaryDetailState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
) = remember(scaffoldState, navController, resources) {
    PrimaryDetailAppState(scaffoldState, navController, resources)
}

@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
