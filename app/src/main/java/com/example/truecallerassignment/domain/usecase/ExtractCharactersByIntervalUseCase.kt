package com.example.truecallerassignment.domain.usecase

import com.example.truecallerassignment.domain.model.TaskResult
import javax.inject.Inject

/**
 * Use case for Task 2: Find every 15th character
 * Time Complexity: O(n/15) where n is the content length
 * Space Complexity: O(n/15) for storing the result list
 */
class ExtractCharactersByIntervalUseCase @Inject constructor() {

    operator fun invoke(content: String, interval: Int): List<Char> {
        return content
            .filterIndexed { index, _ -> (index + 1) % interval == 0 }
            .toList()
    }
}