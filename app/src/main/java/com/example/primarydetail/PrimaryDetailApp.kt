package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MarkEmailRead
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
                            if (appState.inActionMode) {
                                Text(
                                    text = appState.resources.getQuantityString(
                                        R.plurals.count_selected,
                                        appState.selectedItems,
                                        appState.selectedItems
                                    )
                                )
                            } else {
                                Text(text = appState.topBarText)
                            }
                        },
                        navigationIcon = {
                            if (appState.shouldShowBackButton) {
                                IconButton(onClick = appState::upPress) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = stringResource(id = R.string.back),
                                    )
                                }
                            } else if (appState.inActionMode) {
                                IconButton(onClick = appState::upPress) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = stringResource(id = R.string.clear_selected),
                                    )
                                }
                            }
                        },
                        actions = {
                            if (appState.inActionMode) {
                                IconButton(onClick = appState::upPress) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = stringResource(id = R.string.delete),
                                    )
                                }
                                IconButton(onClick = appState::upPress) {
                                    Icon(
                                        imageVector = Icons.Filled.MarkEmailRead,
                                        contentDescription = stringResource(id = R.string.markRead),
                                    )
                                }
                            }
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
