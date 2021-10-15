package com.example.primarydetail

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.primarydetail.MainDestinations.POSTS_LIST_ROUTE

class PrimaryDetailAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val resources: Resources,
) {
    fun upPress() {
        navController.navigateUp()
    }

    val shouldShowBackButton: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route != POSTS_LIST_ROUTE

    val topBarText: String?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.displayName
}

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
