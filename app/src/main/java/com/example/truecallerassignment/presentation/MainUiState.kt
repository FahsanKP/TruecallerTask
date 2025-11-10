package com.example.truecallerassignment.presentation

import com.example.truecallerassignment.domain.model.TaskResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val taskResults: ImmutableList<TaskResult> = persistentListOf(),
)