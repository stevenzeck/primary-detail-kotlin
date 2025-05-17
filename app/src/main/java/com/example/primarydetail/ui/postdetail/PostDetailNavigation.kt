package com.example.primarydetail.ui.postdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.primarydetail.Screen

fun NavController.navigateToPostDetail(postId: Long) =
    this.navigate(
        "${Screen.PostDetail.route}/${postId}"
    )

fun NavGraphBuilder.postDetailScreen() {
    composable(
        route = "${Screen.PostDetail.route}/{postId}",
        arguments = listOf(navArgument("postId") { type = NavType.LongType })
    ) {
        PostDetailScreen()
    }
}
