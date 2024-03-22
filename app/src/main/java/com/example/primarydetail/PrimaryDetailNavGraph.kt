package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

@ExperimentalFoundationApi
@Composable
fun PrimaryDetailNavGraph(
    fm: FragmentManager,
    actions: MainActions,
    appState: PrimaryDetailAppState = rememberPrimaryDetailState(),
    startDestination: String = MainDestinations.POSTS_LIST_ROUTE
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.POSTS_LIST_ROUTE) {
            PostListScreen(
                updateTopBarState = { appState.topBarState = it },
                navigateToPostDetail = actions.navigateToPostDetail,
                navigateToSettings = appState::navigateToSettings,
                resources = appState.resources
            )
        }
        composable(MainDestinations.SETTINGS_ROUTE) {
            SettingsScreen(fm, topBarState = { appState.topBarState = it })
        }
        composable("${MainDestinations.POST_DETAIL_ROUTE}/{$POST_DETAIL_ID_KEY}",
            arguments = listOf(
                navArgument(POST_DETAIL_ID_KEY) {
                    type = NavType.LongType
                }
            )) {
            PostDetailScreen(
                onComposing = { appState.topBarState = it },
                onDeleted = appState::upPress,
            )
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
}
