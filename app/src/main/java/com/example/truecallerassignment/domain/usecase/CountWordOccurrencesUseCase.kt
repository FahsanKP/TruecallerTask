package com.example.truecallerassignment.domain.usecase

import com.example.truecallerassignment.domain.model.TaskResult
import javax.inject.Inject

/**
 * Use case for Task 3: Count word occurrences
 * Time Complexity: O(n) where n is the number of words
 * Space Complexity: O(m) where m is the number of unique words
 */
class CountWordOccurrencesUseCase @Inject constructor() {

    operator fun invoke(content: String): TaskResult.Task3Result {
        // Split by whitespace characters and count occurrences (case insensitive)
        val wordCounts = content
            .split(Regex("\\s+")) // Split by any whitespace
            .filter { it.isNotEmpty() } // Remove empty strings
            .groupingBy { it.lowercase() } // Group by lowercase version
            .eachCount() // Count occurrences
            .toList() // Convert to list for sorting
            .sortedByDescending { it.second } // Sort by count descending
            .toMap() // Convert back to map

        return TaskResult.Task3Result(wordCounts)
    }
}