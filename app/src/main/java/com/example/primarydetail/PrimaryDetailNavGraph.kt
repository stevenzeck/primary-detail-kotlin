package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.primarydetail.MainDestinations.POST_DETAIL_ID_KEY
import com.example.primarydetail.ui.postdetail.PostDetailScreen
import com.example.primarydetail.ui.postlist.PostListScreen
import com.example.primarydetail.ui.settings.SettingsScreen

object MainDestinations {
    const val POSTS_LIST_ROUTE = "postsList"
    const val SETTINGS_ROUTE = "settings"
    const val POST_DETAIL_ROUTE = "postDetail"
    const val POST_DETAIL_ID_KEY = "postId"
}

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun PrimaryDetailNavGraph(
    navController: NavHostController = rememberNavController(),
    fm: FragmentManager,
    appState: PrimaryDetailAppState = rememberPrimaryDetailState(),
    startDestination: String = MainDestinations.POSTS_LIST_ROUTE
) {
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.POSTS_LIST_ROUTE) {
            appState.topBarText = stringResource(id = R.string.title_post_list)
            PostListScreen(
                navigateToPostDetail = actions.navigateToPostDetail,
                navigateToSettings = actions.navigateToSettings,
                selectedPosts = {
                    appState.selectedItems = it
                }
            )
        }
        composable(MainDestinations.SETTINGS_ROUTE) {
            SettingsScreen(fm)
        }
        composable("${MainDestinations.POST_DETAIL_ROUTE}/{$POST_DETAIL_ID_KEY}",
            arguments = listOf(
                navArgument(POST_DETAIL_ID_KEY) {
                    type = NavType.LongType
                }
            )) { backStackEntry ->
            appState.topBarText = stringResource(id = R.string.title_post_detail)
            PostDetailScreen()
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val navigateToPostDetail: (Long) -> Unit = { postId: Long ->
        navController.navigate("${MainDestinations.POST_DETAIL_ROUTE}/${postId}")
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(MainDestinations.SETTINGS_ROUTE)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}