package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import com.example.primarydetail.ui.theme.PrimaryDetailTheme
import com.google.accompanist.insets.ProvideWindowInsets

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun PrimaryDetailApp(fm: FragmentManager) {

    ProvideWindowInsets {
        PrimaryDetailTheme {

            val appState = rememberPrimaryDetailState()

            Scaffold(
                scaffoldState = appState.scaffoldState,
                topBar = {
                    SmallTopAppBar(
                        title = {
                            Text(text = appState.topBarText)
                        },
                        navigationIcon = {
                            if (appState.shouldShowBackButton) {
                                IconButton(onClick = appState::upPress) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = stringResource(id = R.string.back),
                                    )
                                }
                            }
                        },
                        actions = {

                        }
                    )
                },
            ) {
                PrimaryDetailNavGraph(
                    navController = appState.navController,
                    fm = fm,
                    appState = appState,
                )
            }
        }
    }
}
