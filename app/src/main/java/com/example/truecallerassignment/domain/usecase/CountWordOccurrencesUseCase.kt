package com.example.truecallerassignment.domain.usecase

import javax.inject.Inject

class CountWordOccurrencesUseCase @Inject constructor() {

    suspend operator fun invoke(content: String): Map<String, Int> {
        // Split by whitespace characters and count occurrences (case insensitive)
        val wordCounts = content
            .split(Regex("\\s+")) // Split by any whitespace
            .filter { it.isNotEmpty() } // Remove empty strings
            .groupingBy { it.lowercase() } // Group by lowercase version
            .eachCount() // Count occurrences
            .toList() // Convert to list for sorting
            .sortedByDescending { it.second } // Sort by count descending
            .toMap() // Convert back to map

        return wordCounts
    }
}