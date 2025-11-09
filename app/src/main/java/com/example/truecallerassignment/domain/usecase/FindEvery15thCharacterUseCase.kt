package com.example.truecallerassignment.domain.usecase

import com.example.truecallerassignment.domain.model.TaskResult
import javax.inject.Inject

/**
 * Use case for Task 2: Find every 15th character
 * Time Complexity: O(n/15) where n is the content length
 * Space Complexity: O(n/15) for storing the result list
 */
class FindEvery15thCharacterUseCase @Inject constructor() {

    operator fun invoke(content: String): TaskResult.Task2Result {
        val characters = buildList {
            var index = 14 // Start at 15th character (0-indexed)
            while (index < content.length) {
                add(content[index])
                index += 15
            }
        }
        return TaskResult.Task2Result(characters)
    }
}