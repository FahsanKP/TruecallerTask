package com.example.truecallerassignment.presentation

import com.example.truecallerassignment.domain.model.TaskResult

/**
 * Represents the UI state following UDF pattern
 * Immutable data class that contains all UI state
 */
data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val taskResults: List<TaskResult> = listOf(),
)