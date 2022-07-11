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
import com.example.primarydetail.MainDestinations.POST_DETAIL_ROUTE
import com.example.primarydetail.util.ToolbarActionItem

@ExperimentalMaterial3Api
class PrimaryDetailAppState(
    val navController: NavHostController,
    val resources: Resources,
) {
    var topBarText by mutableStateOf("")

    fun upPress() {
        navController.navigateUp()
    }

    private val currentRoute: State<NavBackStackEntry?>
        @Composable get() = navController
            .currentBackStackEntryAsState()

    val showBackButton: Boolean
        @Composable get() = !arrayOf(
            POSTS_LIST_ROUTE,
        ).contains(currentRoute.value?.destination?.route)

    // FIXME Actions on Detail screen no longer work, it shows the list screen buttons
    val screenHasActions: Boolean
        @Composable get() = inActionMode || arrayOf(
            POST_DETAIL_ROUTE,
        ).contains(currentRoute.value?.destination?.route?.substringBefore("/"))

    val inActionMode: Boolean
        @Composable get() = numSelectedItems > 0

    var numSelectedItems: Int by mutableStateOf(0)

    // TODO Can these be moved out of AppState and have the state flow directly to the Scaffold?
    var toolbarActions: List<ToolbarActionItem> = mutableStateListOf()

    var navigationAction: ToolbarActionItem by mutableStateOf(ToolbarActionItem(action = {}))
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