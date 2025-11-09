package com.example.truecallerassignment.data.remote.adapter

import com.example.truecallerassignment.data.remote.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Factory that creates NetworkResultCallAdapter instances
 */
class NetworkResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // Check if return type is Call
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        // Get the inner type (Call<NetworkResult<T>>)
        val callType = getParameterUpperBound(0, returnType as ParameterizedType)

        // Check if it's NetworkResult
        if (getRawType(callType) != NetworkResult::class.java) {
            return null
        }

        // Get the actual data type T from NetworkResult<T>
        val resultType = getParameterUpperBound(0, callType as ParameterizedType)

        return NetworkResultCallAdapter<Any>(resultType)
    }
}