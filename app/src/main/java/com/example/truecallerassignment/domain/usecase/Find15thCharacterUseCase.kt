package com.example.truecallerassignment.domain.usecase

import com.example.truecallerassignment.domain.model.TaskResult
import javax.inject.Inject

/**
 * Use case for Task 1: Find the 15th character
 * Time Complexity: O(1)
 * Space Complexity: O(1)
 */
class Find15thCharacterUseCase @Inject constructor() {

    operator fun invoke(content: String): TaskResult.Task1Result {
        val character = if (content.length >= 15) {
            content[14] // 0-indexed, so 15th character is at index 14
        } else {
            null
        }
        return TaskResult.Task1Result(character)
    }
}
