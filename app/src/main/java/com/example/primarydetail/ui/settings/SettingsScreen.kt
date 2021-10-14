package com.example.primarydetail.ui.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.primarydetail.R
import com.example.primarydetail.settings.SettingsFragment

@Composable
fun SettingsScreen(onBack: () -> Unit, fm: FragmentManager) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_settings)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                        )
                    }
                }
            )
        },
        content = {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    val view = FragmentContainerView(it)
                    view.id = R.id.settings
                    fm.beginTransaction().add(view.id, SettingsFragment()).commit()
                    view
                },
                update = {}
            )
        }
    )
}