package com.example.primarydetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.primarydetail.ui.postdetail.PostDetailViewModel
import com.example.primarydetail.ui.theme.PrimaryDetailTheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryEntryPoint {
        fun postDetailViewModelFactory(): PostDetailViewModel.Factory
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postDetailViewModelFactory = EntryPointAccessors.fromActivity(
            this,
            ViewModelFactoryEntryPoint::class.java
        ).postDetailViewModelFactory()

        setContent {
            PrimaryDetailTheme {
                PrimaryDetailApp(postDetailViewModelFactory = postDetailViewModelFactory)
            }
        }
    }
}
