package com.example.primarydetail.ui.postdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.primarydetail.R
import com.example.primarydetail.model.Post
import com.example.primarydetail.ui.postlist.Loading
import com.example.primarydetail.util.TopBarState

@Composable
fun PostDetailScreen(
    updateTopBarState: (TopBarState) -> Unit,
    onPostDeleted: () -> Unit,
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.postDetailUiState.collectAsStateWithLifecycle()

    updateTopBarState(
        TopBarState(
            title = stringResource(id = R.string.title_post_detail),
            actions = {
                IconButton(onClick = {
                    viewModel.deletePost()
                    onPostDeleted()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.delete),
                    )
                }
            }
        )
    )

    when (val currentState = uiState) {
        is PostDetailUiState.Success -> PostDetailContent(
            post = currentState.post,
        )

        is PostDetailUiState.Failed -> Loading()
        is PostDetailUiState.Loading -> Loading()
    }
}

@Composable
fun PostDetailContent(post: Post) {
    Column {
        Row(modifier = Modifier.padding(20.dp)) {
            SelectionContainer {
                Text(text = post.title, fontSize = 30.sp)
            }
        }
        Row(modifier = Modifier.padding(20.dp)) {
            SelectionContainer {
                Text(text = post.body, fontSize = 18.sp)
            }
        }
    }
}
