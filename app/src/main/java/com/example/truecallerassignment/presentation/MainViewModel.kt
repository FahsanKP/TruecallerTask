package com.example.truecallerassignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truecallerassignment.data.remote.NetworkResult
import com.example.truecallerassignment.domain.usecase.CountWordOccurrencesUseCase
import com.example.truecallerassignment.domain.usecase.FetchWebContentUseCase
import com.example.truecallerassignment.domain.usecase.Find15thCharacterUseCase
import com.example.truecallerassignment.domain.usecase.FindEvery15thCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel following MVVM pattern with UDF
 * Manages UI state and business logic
 *
 * State flows down to UI, events flow up from UI
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchWebContentUseCase: FetchWebContentUseCase,
    private val find15thCharacterUseCase: Find15thCharacterUseCase,
    private val findEvery15thCharacterUseCase: FindEvery15thCharacterUseCase,
    private val countWordOccurrencesUseCase: CountWordOccurrencesUseCase
) : ViewModel() {

    companion object {
        private const val TARGET_URL = "https://www.truecaller.com/blog/life-at-truecaller/life-as-an-android-engineer"
    }

    // Private mutable state
    private val _uiState = MutableStateFlow(MainUiState())

    // Public immutable state exposed to UI
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    /**
     * Handle UI events
     * Single entry point for all user interactions
     */
    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.LoadContent -> loadContentAndProcessTasks()
            is MainUiEvent.ClearResults -> clearResults()
        }
    }

    /**
     * Loads web content and executes all three tasks in parallel
     * Each task updates the UI as soon as it completes
     */
    private fun loadContentAndProcessTasks() {
        viewModelScope.launch {
            // Set initial loading state
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Fetch web content
            when (val result = fetchWebContentUseCase(TARGET_URL)) {
                is NetworkResult.Success -> {
                    val content = result.data.content

                    // Update loading state - content fetched
                    _uiState.update { it.copy(isLoading = false) }

                    // Execute all three tasks in parallel
                    // Each task will update UI state independently when complete

                    // Task 1: Find 15th character
                    launch {
                        _uiState.update { it.copy(task1Loading = true) }
                        val task1Result = find15thCharacterUseCase(content)
                        _uiState.update {
                            it.copy(
                                task1Result = task1Result,
                                task1Loading = false
                            )
                        }
                    }

                    // Task 2: Find every 15th character
                    launch {
                        _uiState.update { it.copy(task2Loading = true) }
                        val task2Result = findEvery15thCharacterUseCase(content)
                        _uiState.update {
                            it.copy(
                                task2Result = task2Result,
                                task2Loading = false
                            )
                        }
                    }

                    // Task 3: Count word occurrences
                    launch {
                        _uiState.update { it.copy(task3Loading = true) }
                        val task3Result = countWordOccurrencesUseCase(content)
                        _uiState.update {
                            it.copy(
                                task3Result = task3Result,
                                task3Loading = false
                            )
                        }
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    // Already handled above
                }
            }
        }
    }

    /**
     * Clears all results and resets the state
     */
    private fun clearResults() {
        _uiState.update { MainUiState() }
    }
}
