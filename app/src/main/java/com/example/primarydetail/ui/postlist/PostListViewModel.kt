package com.example.primarydetail.ui.postlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.SQLiteException
import com.example.primarydetail.R
import com.example.primarydetail.ui.PostRepository
import com.example.primarydetail.util.AppError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    val uiState: StateFlow<PostListUiState> = repository.getPosts()
        .map { posts -> PostListUiState.Success(posts) as PostListUiState } // Cast to base type
        .catch { e -> emit(PostListUiState.Failed(mapExceptionToAppError(e))) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PostListUiState.Loading
        )

    fun markRead(postIds: List<Long>) = viewModelScope.launch {
        try {
            if (postIds.isNotEmpty()) {
                repository.markRead(postIds)
            }
        } catch (e: Exception) {
            Log.e("PostDetailViewModel", "Failed to mark multiple posts as read", e)
        }
    }

    fun markRead(postId: Long) = viewModelScope.launch {
        try {
            repository.markRead(postId = postId)
        } catch (e: Exception) {
            Log.e("PostDetailViewModel", "Failed to mark post as read", e)
        }
    }

    fun deletePosts(postIds: List<Long>) = viewModelScope.launch {
        try {
            if (postIds.isNotEmpty()) {
                repository.deletePosts(postIds)
            }
        } catch (e: Exception) {
            Log.e("PostDetailViewModel", "Failed to delete multiple posts", e)
        }
    }

    private fun mapExceptionToAppError(e: Throwable): AppError {
        return when (e) {
            is IOException -> AppError.NetworkError(
                R.string.error_network,
                specificMessage = e.message
            )

            is HttpException -> AppError.NetworkError(
                R.string.error_network_with_code,
                statusCode = e.code(),
                specificMessage = e.message()
            )

            is SQLiteException -> AppError.DatabaseError(
                R.string.error_database,
                specificMessage = e.message
            )

            else -> AppError.UnknownError(R.string.error_unknown, specificMessage = e.message)
        }
    }
}
