package com.example.primarydetail.ui.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.primarydetail.R
import com.example.primarydetail.settings.SettingsFragment
import com.example.primarydetail.util.TopBarState

@Composable
fun SettingsScreen(fm: FragmentManager, topBarState: (TopBarState) -> Unit) {
    topBarState(
        TopBarState(
            title = stringResource(id = R.string.title_settings)
        )
    )
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