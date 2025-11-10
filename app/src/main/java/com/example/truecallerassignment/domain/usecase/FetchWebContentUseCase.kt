package com.example.truecallerassignment.domain.usecase

import com.example.truecallerassignment.data.remote.NetworkResult
import com.example.truecallerassignment.domain.repository.WebContentRepository
import javax.inject.Inject

class FetchWebContentUseCase @Inject constructor(
    private val repository: WebContentRepository
) {
    suspend operator fun invoke(url: String): NetworkResult<String> {
        return repository.fetchWebContent(url)
    }
}