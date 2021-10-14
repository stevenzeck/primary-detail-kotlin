package com.example.primarydetail.ui.postdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.primarydetail.R
import com.example.primarydetail.model.Post
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PostDetailScreen(postId: Long?, onBack: () -> Unit) {
    val viewModel = getViewModel<PostDetailViewModel> { parametersOf(postId) }

    val uiState by viewModel.uiState.collectAsState()

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_post_detail)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        postId?.let {
                            viewModel.deletePost(postId)
                            onBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete),
                        )
                    }
                }
            )
        },
        content = {
            if (uiState.post != null) {
                PostDetailContent(
                    post = uiState.post!!,
                )
            }

            // Check for failures while loading the state
            // TODO: Improve UX
            LaunchedEffect(uiState) {
                if (uiState.failedLoading) {
                    onBack()
                }
            }
        }
    )
}

@Composable
fun PostDetailContent(post: Post) {
    Column {
        Row(modifier = Modifier.padding(20.dp)) {
            Text(text = post.title, fontSize = 30.sp)
        }
        Row(modifier = Modifier.padding(20.dp)) {
            Text(text = post.body, fontSize = 18.sp)
        }
    }
}
