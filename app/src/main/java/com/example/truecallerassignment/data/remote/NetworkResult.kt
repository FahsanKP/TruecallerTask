package com.example.truecallerassignment.data.remote

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()

    data class Error(
        val message: String,
        val type: ErrorType = ErrorType.UNKNOWN,
        val code: Int? = null
    ) : NetworkResult<Nothing>()
}

enum class ErrorType {
    NETWORK,
    TIMEOUT,
    SERVER,
    CLIENT,
    PARSING,
    UNKNOWN
}
