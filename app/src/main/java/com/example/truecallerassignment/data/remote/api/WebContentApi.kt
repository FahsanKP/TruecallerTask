package com.example.truecallerassignment.data.remote.api

import com.example.truecallerassignment.data.remote.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Retrofit API interface for fetching web content
 */
interface WebContentApi {

    /**
     * Fetches the plain text content from a given URL
     * @param url The complete URL to fetch
     * @return Plain text content as String
     */
    @GET
    suspend fun fetchWebContent(@Url url: String): NetworkResult<String>
}