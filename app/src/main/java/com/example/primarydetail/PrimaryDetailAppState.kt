package com.example.primarydetail

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalResources
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.primarydetail.util.TopBarState

class PrimaryDetailAppState(
    val navController: NavHostController,
    val resources: Resources,
) {

    fun upPress() {
        navController.navigateUp()
    }

    private val currentRoute: State<NavBackStackEntry?>
        @Composable get() = navController
            .currentBackStackEntryAsState()

    val showBackButton: Boolean
        @Composable get() = !arrayOf(
            Screen.PostList.route,
        ).contains(currentRoute.value?.destination?.route)
}

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
    return LocalResources.current
}
