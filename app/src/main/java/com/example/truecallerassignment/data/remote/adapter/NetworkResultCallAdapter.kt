package com.example.truecallerassignment.data.remote.adapter

import com.example.truecallerassignment.data.remote.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * CallAdapter that wraps Retrofit responses in NetworkResult
 * This is called BEFORE the response reaches the repository
 */
class NetworkResultCallAdapter<T>(
    private val responseType: Type
) : CallAdapter<T, Call<NetworkResult<T>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<NetworkResult<T>> {
        return NetworkResultCall(call)
    }
}