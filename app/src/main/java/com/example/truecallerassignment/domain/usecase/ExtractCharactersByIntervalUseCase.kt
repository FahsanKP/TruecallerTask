package com.example.truecallerassignment.domain.usecase

import javax.inject.Inject

class ExtractCharactersByIntervalUseCase @Inject constructor() {

    suspend operator fun invoke(content: String, interval: Int, elementsPerChunk: Int): List<List<Char>> {
        val characters =  content
            .filterIndexed { index, _ -> (index + 1) % interval == 0 }
            .toList()
        return characters.chunked(elementsPerChunk)
    }
}