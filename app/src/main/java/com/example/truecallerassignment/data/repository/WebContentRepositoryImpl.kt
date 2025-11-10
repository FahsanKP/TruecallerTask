package com.example.truecallerassignment.data.repository

import com.example.truecallerassignment.data.remote.NetworkResult
import com.example.truecallerassignment.data.remote.api.WebContentApi
import com.example.truecallerassignment.domain.repository.WebContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WebContentRepositoryImpl @Inject constructor(
    private val api: WebContentApi
) : WebContentRepository {

    override suspend fun fetchWebContent(): NetworkResult<String> {
        return withContext(Dispatchers.IO) {
            api.fetchWebContent()
        }
    }
}