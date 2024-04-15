package com.example.primarydetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import com.example.primarydetail.ui.theme.PrimaryDetailTheme

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun PrimaryDetailApp(fragmentManager: FragmentManager) {

    PrimaryDetailTheme {

        val appState = rememberPrimaryDetailState()

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
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                    fragmentManager = fragmentManager,
                    appState = appState,
                )
            }
        }
    }
}
