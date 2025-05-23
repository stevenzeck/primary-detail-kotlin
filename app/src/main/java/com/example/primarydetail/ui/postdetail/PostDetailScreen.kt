package com.example.primarydetail.ui.postdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.primarydetail.model.Post
import com.example.primarydetail.ui.postlist.LoadingView

@Suppress("UNCHECKED_CAST")
class LambdaViewModelFactory<T : ViewModel>(
    private val create: () -> T
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create() as T
    }
}

@Composable
fun PostDetailScreen(
    postId: Long,
    viewModelFactory: PostDetailViewModel.Factory,
) {
    val viewModel: PostDetailViewModel = viewModel(
        key = "post_detail_$postId",
        factory = LambdaViewModelFactory { viewModelFactory.create(postId) }
    )
    val uiState by viewModel.postDetailUiState.collectAsStateWithLifecycle()

    when (val currentState = uiState) {
        is PostDetailUiState.Success -> PostDetailContent(
            post = currentState.post,
        )

        is PostDetailUiState.Failed -> LoadingView()
        is PostDetailUiState.Loading -> LoadingView()
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
