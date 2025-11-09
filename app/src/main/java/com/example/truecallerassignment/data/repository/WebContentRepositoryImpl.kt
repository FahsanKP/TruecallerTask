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

    override suspend fun fetchWebContent(url: String): NetworkResult<WebContent> {
        return when (val result = api.fetchWebContent(url)) {
            is NetworkResult.Success -> {
                // Map String to WebContent domain model
                NetworkResult.Success(
                    WebContent(url = url, content = result.data)
                )
            }
            is NetworkResult.Error -> {
                // Pass error through
                result
            }
            is NetworkResult.Loading -> {
                result
            }
        }
    }
}