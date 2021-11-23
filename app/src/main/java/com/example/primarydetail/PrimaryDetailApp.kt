package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
            val actions = remember(appState.navController) { MainActions(appState.navController) }

            Scaffold(
                scaffoldState = appState.scaffoldState,
                topBar = {
                    SmallTopAppBar(
                        title = {
                            if (appState.inActionMode) {
                                Text(
                                    text = appState.resources.getQuantityString(
                                        R.plurals.count_selected,
                                        appState.numSelectedItems,
                                        appState.numSelectedItems
                                    )
                                )
                            } else {
                                Text(text = appState.topBarText)
                            }
                        },
                        navigationIcon = {
                            if (appState.showBackButton) {
                                IconButton(onClick = appState::upPress) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = stringResource(id = R.string.back),
                                    )
                                }
                            } else if (appState.inActionMode) {
                                appState.navigationAction.apply {
                                    IconButton(onClick = action) {
                                        Icon(
                                            imageVector = icon!!,
                                            contentDescription = description
                                        )
                                    }
                                }
                            }
                        },
                        actions = {
                            if (appState.screenHasActions) {
                                appState.toolbarActions.forEach {
                                    IconButton(onClick = it.action) {
                                        Icon(
                                            imageVector = it.icon!!,
                                            contentDescription = it.description,
                                        )
                                    }
                                }
                            } else {
                                IconButton(onClick = actions.navigateToSettings) {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = stringResource(id = R.string.title_settings),
                                    )
                                }
                            }
                        }
                    )
                },
            ) {
                PrimaryDetailNavGraph(
                    fm = fm,
                    actions = actions,
                    appState = appState,
                )
            }
        }
    }
}
