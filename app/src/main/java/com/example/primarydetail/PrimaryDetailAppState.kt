package com.example.primarydetail

import android.content.res.Resources
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldState
import androidx.compose.material3.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.primarydetail.MainDestinations.POSTS_LIST_ROUTE
import com.example.primarydetail.MainDestinations.POST_DETAIL_ROUTE
import com.example.primarydetail.MainDestinations.SETTINGS_ROUTE

@ExperimentalMaterial3Api
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

    // FIXME there has to be a better way for this. And need a way to change the title when in selection mode
    val topBarText: String
        @Composable get() = when (navController
            .currentBackStackEntryAsState().value?.destination?.route) {
            POSTS_LIST_ROUTE -> stringResource(id = R.string.title_post_list)
            "$POST_DETAIL_ROUTE/{postId}" -> stringResource(id = R.string.title_post_detail)
            SETTINGS_ROUTE -> stringResource(id = R.string.title_settings)
            else -> stringResource(id = R.string.app_name)
        }
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
