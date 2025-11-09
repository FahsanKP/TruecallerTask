package com.example.truecallerassignment.domain.repository

import com.example.truecallerassignment.data.remote.NetworkResult
import com.example.truecallerassignment.domain.model.WebContent

/**
 * Repository interface following the Repository pattern
 * Abstracts the data source from the domain layer
 */
interface WebContentRepository {
    /**
     * Fetches web content from the given URL
     * @param url The URL to fetch content from
     * @return NetworkResult wrapping the WebContent
     */
    suspend fun fetchWebContent(url: String): NetworkResult<WebContent>
}