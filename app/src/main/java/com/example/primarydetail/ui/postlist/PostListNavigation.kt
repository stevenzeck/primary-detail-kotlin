package com.example.primarydetail.ui.postlist

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.primarydetail.Screen
import com.example.primarydetail.model.Post
import com.example.primarydetail.util.TopBarState

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.postListScreen(
    updateTopBarState: (TopBarState) -> Unit,
    navigateToSettings: () -> Unit,
    onPostSelected: (Post) -> Unit,
    resources: Resources
) {
    composable(route = Screen.PostList.route) {
        PostListScreen(
            updateTopBarState = updateTopBarState,
            navigateToSettings = navigateToSettings,
            onPostSelected = onPostSelected,
            resources = resources
        )
    }
}
