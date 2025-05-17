package com.example.primarydetail

import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import androidx.navigation.compose.NavHost
import com.example.primarydetail.ui.postdetail.navigateToPostDetail
import com.example.primarydetail.ui.postdetail.postDetailScreen
import com.example.primarydetail.ui.postlist.postListScreen
import com.example.primarydetail.ui.settings.settingsScreen

@ExperimentalFoundationApi
@Composable
fun PrimaryDetailNavGraph(
    fragmentManager: FragmentManager,
    appState: PrimaryDetailAppState = rememberPrimaryDetailState(),
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.PostList.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {

        postListScreen(
            onPostSelected = {
                appState.navController.navigateToPostDetail(it)
            },
        )

        postDetailScreen()

        settingsScreen(
            fragmentManager = fragmentManager,
            updateTopBarState = {
                appState.topBarState = it
            }
        )

    }
}

sealed class Screen(val route: String, @StringRes val title: Int? = null) {
    object PostList : Screen("postlist", R.string.title_post_list)
    object PostDetail : Screen("postdetail", R.string.title_post_detail)
    object Settings : Screen("settings", R.string.title_settings)
}
