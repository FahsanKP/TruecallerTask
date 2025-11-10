package com.example.truecallerassignment.data.repository

import com.example.truecallerassignment.data.remote.NetworkResult
import com.example.truecallerassignment.data.remote.api.WebContentApi
import com.example.truecallerassignment.domain.model.WebContent
import com.example.truecallerassignment.domain.repository.WebContentRepository
import javax.inject.Inject

/**
 * Implementation of WebContentRepository
 * Handles data fetching and error handling
 */
class WebContentRepositoryImpl @Inject constructor(
    private val api: WebContentApi
) : WebContentRepository {

    override suspend fun fetchWebContent(url: String): NetworkResult<String> {
        return api.fetchWebContent(url)
    }
}