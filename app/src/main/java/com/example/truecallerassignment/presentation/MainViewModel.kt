package com.example.truecallerassignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truecallerassignment.data.remote.NetworkResult
import com.example.truecallerassignment.domain.model.CharacterResult
import com.example.truecallerassignment.domain.model.CharactersListResult
import com.example.truecallerassignment.domain.model.TaskResult
import com.example.truecallerassignment.domain.model.WordFrequencyResult
import com.example.truecallerassignment.domain.usecase.CountWordOccurrencesUseCase
import com.example.truecallerassignment.domain.usecase.FetchWebContentUseCase
import com.example.truecallerassignment.domain.usecase.GetCharacterAtPositionUseCase
import com.example.truecallerassignment.domain.usecase.ExtractCharactersByIntervalUseCase
import com.truecaller.task.R
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
    private val getCharacterAtPositionUseCase: GetCharacterAtPositionUseCase,
    private val extractCharactersByIntervalUseCase: ExtractCharactersByIntervalUseCase,
    private val countWordOccurrencesUseCase: CountWordOccurrencesUseCase
) : ViewModel() {

    companion object {
        private const val TARGET_URL =
            "https://www.truecaller.com/blog/life-at-truecaller/life-as-an-android-engineer"
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
        }
    }

    /**
     * Loads web content and executes all three tasks in parallel
     * Each task updates the UI as soon as it completes
     */
    private fun loadContentAndProcessTasks() {
        viewModelScope.launch {
            // Set initial loading state
            _uiState.update { it.copy(isLoading = true) }

            // Fetch web content
            when (val result = fetchWebContentUseCase(TARGET_URL)) {
                is NetworkResult.Success -> {
                    val content = result.data

                    // Update loading state - content fetched
                    _uiState.update { it.copy(isLoading = false) }

                    // Execute all three tasks in parallel
                    // Each task will update UI state independently when complete

                    // Task 1: Find 15th character
                    launch {
                        getCharacterAtPosition(content)
                    }

                    // Task 2: Find every 15th character
                    launch {
                        getEveryFifteenthCharacters(content)
                    }

                    // Task 3: Count word occurrences
                    launch {
                        getWordCountOccurences(content)
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false, error = result.message
                        )
                    }
                }
            }
        }
    }

    private fun getWordCountOccurences(content: String) {
        val wordCount = countWordOccurrencesUseCase(content)
        val taskResult = WordFrequencyResult(
            title = R.string.task3_title,
            wordCounts = wordCount
        )
        updateUiStateTasks(taskResult)
    }

    private fun getEveryFifteenthCharacters(content: String) {
        val extractedCharacters = extractCharactersByIntervalUseCase(content, 15)
        val taskResult = CharactersListResult(
            title = R.string.task2_title,
            characters = extractedCharacters
        )
        updateUiStateTasks(taskResult)
    }

    private fun getCharacterAtPosition(content: String) {
        val charAtPosition =
            getCharacterAtPositionUseCase(content = content, position = 14)
        val taskResult = CharacterResult(
            title = R.string.task1_title,
            character = charAtPosition
        )
        updateUiStateTasks(taskResult)
    }

    private fun updateUiStateTasks(taskResult: TaskResult) {
        _uiState.update {
            it.copy(
                taskResults = it.taskResults.plus(taskResult),
            )
        }
    }
}
