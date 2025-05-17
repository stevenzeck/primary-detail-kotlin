package com.example.primarydetail.ui.postlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.primarydetail.Screen

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.postListScreen(
    onPostSelected: (Long) -> Unit,
) {
    composable(route = Screen.PostList.route) {
        PostListScreen(
            onPostSelected = onPostSelected,
        )
    }
}
