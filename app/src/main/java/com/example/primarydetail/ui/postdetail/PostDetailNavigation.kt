package com.example.primarydetail.ui.postdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.primarydetail.Screen
import com.example.primarydetail.util.TopBarState

fun NavController.navigateToPostDetail(postId: Long) =
    this.navigate(
        "${Screen.PostDetail.route}/${postId}"
    )

fun NavGraphBuilder.postDetailScreen(
    updateTopBarState: (TopBarState) -> Unit,
    onPostDeleted: () -> Unit,
) {
    composable(route = "${Screen.PostDetail.route}/{postId}") {
        PostDetailScreen(
            updateTopBarState = updateTopBarState,
            onPostDeleted = onPostDeleted,
        )
    }
}
