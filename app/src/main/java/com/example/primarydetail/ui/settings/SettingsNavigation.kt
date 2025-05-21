package com.example.primarydetail.ui.settings

import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.primarydetail.Screen

fun NavController.navigateToSettings() = this.navigate(
    Screen.Settings.route
)

fun NavGraphBuilder.settingsScreen(
    fragmentManager: FragmentManager,
) {
    composable(route = Screen.Settings.route) {
        SettingsScreen(
            fragmentManager = fragmentManager,
        )
    }
}
