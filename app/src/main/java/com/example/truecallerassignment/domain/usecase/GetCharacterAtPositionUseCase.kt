package com.example.truecallerassignment.domain.usecase

import com.example.truecallerassignment.domain.model.TaskResult
import javax.inject.Inject

/**
 * Use case for Task 1: Find the 15th character
 * Time Complexity: O(1)
 * Space Complexity: O(1)
 */
class GetCharacterAtPositionUseCase @Inject constructor() {

    operator fun invoke(content: String, position: Int): Char? {
        return content.getOrNull(position)
    }
}
