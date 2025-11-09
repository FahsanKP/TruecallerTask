package com.example.truecallerassignment.data.remote

/**
 * Sealed class representing network operation results
 */
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()

    data class Error(
        val message: String,
        val type: ErrorType = ErrorType.UNKNOWN,
        val code: Int? = null,
        val exception: Throwable? = null
    ) : NetworkResult<Nothing>()

    data object Loading : NetworkResult<Nothing>()
}

enum class ErrorType {
    NETWORK,    // No internet
    TIMEOUT,    // Request timeout
    SERVER,     // 5xx errors
    CLIENT,     // 4xx errors
    PARSING,    // Data parsing errors
    UNKNOWN     // Other errors
}
