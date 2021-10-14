package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    navController: NavHostController = rememberNavController(),
    fm: FragmentManager,
    startDestination: String = MainDestinations.POSTS_LIST_ROUTE
) {
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.POSTS_LIST_ROUTE) {
            PostListScreen(
                navigateToPostDetail = actions.navigateToPostDetail,
                navigateToSettings = actions.navigateToSettings
            )
        }
        composable(MainDestinations.SETTINGS_ROUTE) {
            SettingsScreen(onBack = actions.upPress, fm)
        }
        composable("${MainDestinations.POST_DETAIL_ROUTE}/{$POST_DETAIL_ID_KEY}") { backStackEntry ->
            PostDetailScreen(
                postId = backStackEntry.arguments?.getString(POST_DETAIL_ID_KEY)?.toLong(),
                onBack = actions.upPress,
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
    val navigateToSettings: () -> Unit = {
        navController.navigate(MainDestinations.SETTINGS_ROUTE)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}