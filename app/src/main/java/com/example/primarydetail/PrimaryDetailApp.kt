package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import com.example.primarydetail.ui.theme.PrimaryDetailTheme

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun PrimaryDetailApp(fm: FragmentManager) {

    PrimaryDetailTheme {

        val appState = rememberPrimaryDetailState()
        val actions = remember(appState.navController) { MainActions(appState.navController) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = appState.topBarState.title)
                    },
                    navigationIcon = {
                        if (appState.showBackButton) {
                            // FIXME this is a jarring transition
                            IconButton(onClick = appState::upPress) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back),
                                )
                            }
                        } else appState.topBarState.navigationAction?.invoke()
                    },
                    actions = {
                        appState.topBarState.actions?.invoke(this)
                    }
                )
            },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                PrimaryDetailNavGraph(
                    fm = fm,
                    actions = actions,
                    appState = appState,
                )
            }
        }
    }
}
