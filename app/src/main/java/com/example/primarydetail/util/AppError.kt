package com.example.primarydetail.util

sealed interface AppError {
    data class NetworkError(
        val messageResource: Int,
        val statusCode: Int? = null,
        val specificMessage: String? = null
    ) : AppError

    data class DatabaseError(val messageResource: Int, val specificMessage: String? = null) :
        AppError

    data class UnknownError(val messageResource: Int, val specificMessage: String? = null) :
        AppError
}