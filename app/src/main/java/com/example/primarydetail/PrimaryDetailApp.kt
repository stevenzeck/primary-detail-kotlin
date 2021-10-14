package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import com.example.primarydetail.ui.theme.PrimaryDetailTheme
import com.google.accompanist.insets.ProvideWindowInsets

@ExperimentalFoundationApi
@Composable
fun PrimaryDetailApp(fm: FragmentManager) {

    ProvideWindowInsets {
        PrimaryDetailTheme {

            val appState = rememberPrimaryDetailState()

            Scaffold(
                scaffoldState = appState.scaffoldState,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "")
                        },
                        actions = {

                        }
                    )
                },
            ) {
                PrimaryDetailNavGraph(
                    navController = appState.navController,
                    fm = fm,
                )
            }
        }
    }
}
