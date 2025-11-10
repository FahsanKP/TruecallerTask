package com.example.truecallerassignment.domain.model

import androidx.annotation.StringRes

sealed interface TaskResult {
    val title: Int
}

data class CharacterResult(@param:StringRes override val title: Int, val character: Char?) :
    TaskResult

data class CharactersListResult(
    @param:StringRes override val title: Int,
    val chunkedCharacters: List<List<Char>>
) : TaskResult

data class WordFrequencyResult(
    @param:StringRes override val title: Int,
    val wordCounts: Map<String, Int>
) : TaskResult
