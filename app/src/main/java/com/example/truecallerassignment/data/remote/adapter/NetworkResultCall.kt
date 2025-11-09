package com.example.truecallerassignment.data.remote.adapter

import com.example.truecallerassignment.data.remote.ErrorType
import com.example.truecallerassignment.data.remote.NetworkResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Custom Call implementation that wraps the response
 */
internal class NetworkResultCall<T>(
    private val delegate: Call<T>
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        NetworkResult.Success(body)
                    } else {
                        NetworkResult.Error(
                            message = "Response body is null",
                            type = ErrorType.PARSING,
                            code = response.code()
                        )
                    }
                } else {
                    handleHttpError(response)
                }

                callback.onResponse(this@NetworkResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val networkResult = mapThrowableToNetworkError(throwable)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }
        })
    }

    /**
     * Maps Throwable exceptions to NetworkResult.Error with appropriate error types and messages
     *
     * @param throwable The exception thrown during network request
     * @return NetworkResult.Error with user-friendly message and error type
     */
    private fun mapThrowableToNetworkError(throwable: Throwable): NetworkResult.Error {
        val message: String
        val type: ErrorType

        when (throwable) {
            is SocketTimeoutException -> {
                message = "Request timeout. Please try again."
                type = ErrorType.TIMEOUT
            }

            is UnknownHostException -> {
                message = "No internet connection. Please check your network."
                type = ErrorType.NETWORK
            }

            is IOException -> {
                message = "Network error occurred. Please try again."
                type = ErrorType.NETWORK
            }

            else -> {
                message = throwable.message ?: "An unexpected error occurred"
                type = ErrorType.UNKNOWN
            }
        }

        return NetworkResult.Error(
            message = message,
            type = type,
            exception = throwable
        )
    }

    /**
     * Maps HTTP error responses to NetworkResult.Error
     */
    private fun handleHttpError(response: Response<T>): NetworkResult.Error {
        val errorMessage = response.errorBody()?.string() ?: response.message()
        val errorType = when (response.code()) {
            in 400..499 -> ErrorType.CLIENT
            in 500..599 -> ErrorType.SERVER
            else -> ErrorType.UNKNOWN
        }

        return NetworkResult.Error(
            message = "HTTP ${response.code()}: $errorMessage",
            type = errorType,
            code = response.code()
        )
    }

    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun execute(): Response<NetworkResult<T>> =
        throw UnsupportedOperationException("Only async calls supported")

    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(delegate.clone())
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}
