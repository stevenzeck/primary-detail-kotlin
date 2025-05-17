package com.example.primarydetail.ui.postdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.SQLiteException
import com.example.primarydetail.R
import com.example.primarydetail.model.Post
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
class PostDetailViewModel @Inject constructor(
    private val repository: PostRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val postId: Long = savedStateHandle["postId"] ?: 0L

    val postDetailUiState: StateFlow<PostDetailUiState> =
        repository.postById(postId)
            .map<Post, PostDetailUiState>(PostDetailUiState::Success)
            .catch { exception ->
                emit(PostDetailUiState.Failed(mapExceptionToAppError(exception)))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PostDetailUiState.Loading,
            )

    /**
     * Mark a post as read via repository
     * @param postId The ID of the post to mark as read
     */
    fun markRead(postId: Long) = viewModelScope.launch {
        try {
            repository.markRead(postId)
        } catch (e: Exception) {

        }
    }

    /**
     * Delete a post via repository
     */
    fun deletePost() = viewModelScope.launch {
        try {
            postId.let {
                repository.deletePost(it)
            }
        } catch (e: Exception) {

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
