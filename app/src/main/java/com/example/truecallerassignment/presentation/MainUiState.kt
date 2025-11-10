package com.example.truecallerassignment.presentation

import com.example.truecallerassignment.domain.model.TaskResult

data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val taskResults: List<TaskResult> = listOf(),
)