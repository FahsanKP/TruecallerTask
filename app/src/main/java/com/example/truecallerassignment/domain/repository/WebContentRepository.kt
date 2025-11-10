package com.example.truecallerassignment.domain.repository

import com.example.truecallerassignment.data.remote.NetworkResult

interface WebContentRepository {
    suspend fun fetchWebContent(): NetworkResult<String>
}