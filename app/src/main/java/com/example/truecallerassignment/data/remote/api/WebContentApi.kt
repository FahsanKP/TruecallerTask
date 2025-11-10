package com.example.truecallerassignment.data.remote.api

import com.example.truecallerassignment.data.remote.NetworkResult
import retrofit2.http.GET

interface WebContentApi {
    // Fetches web content from the fixed blog URL
    @GET("blog/life-at-truecaller/life-as-an-android-engineer")
    suspend fun fetchWebContent(): NetworkResult<String>
}