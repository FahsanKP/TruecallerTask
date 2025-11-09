package com.example.truecallerassignment.presentation

import com.example.truecallerassignment.domain.model.TaskResult

/**
 * Represents the UI state following UDF pattern
 * Immutable data class that contains all UI state
 */
data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val task1Result: TaskResult.Task1Result? = null,
    val task2Result: TaskResult.Task2Result? = null,
    val task3Result: TaskResult.Task3Result? = null,
    val task1Loading: Boolean = false,
    val task2Loading: Boolean = false,
    val task3Loading: Boolean = false
)