package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import androidx.navigation.compose.rememberNavController
import com.example.primarydetail.ui.theme.PrimaryDetailTheme
import com.google.accompanist.insets.ProvideWindowInsets

@ExperimentalFoundationApi
@Composable
fun PrimaryDetailApp(fm: FragmentManager) {
    ProvideWindowInsets {
        PrimaryDetailTheme {

            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            Scaffold(
                scaffoldState = scaffoldState,
            ) {

                PrimaryDetailNavGraph(
                    navController = navController,
                    fm
                )
            }

        }
    }
}
