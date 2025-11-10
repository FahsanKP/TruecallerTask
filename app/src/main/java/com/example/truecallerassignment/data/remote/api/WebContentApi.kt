package com.example.truecallerassignment.data.remote.api

import com.example.truecallerassignment.data.remote.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Url

interface WebContentApi {
    @GET
    suspend fun fetchWebContent(@Url url: String): NetworkResult<String>
}