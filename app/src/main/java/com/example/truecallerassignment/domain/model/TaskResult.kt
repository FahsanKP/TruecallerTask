package com.example.truecallerassignment.domain.model

/**
 * Sealed class representing the result of each task
 */
sealed class TaskResult {
    data class Task1Result(val character: Char?) : TaskResult()
    data class Task2Result(val characters: List<Char>) : TaskResult()
    data class Task3Result(val wordCounts: Map<String, Int>) : TaskResult()
}