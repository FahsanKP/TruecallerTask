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
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchWebContentUseCase: FetchWebContentUseCase,
    private val getCharacterAtPositionUseCase: GetCharacterAtPositionUseCase,
    private val extractCharactersByIntervalUseCase: ExtractCharactersByIntervalUseCase,
    private val countWordOccurrencesUseCase: CountWordOccurrencesUseCase
) : ViewModel() {

    // Private mutable state
    private val _uiState = MutableStateFlow(MainUiState())

    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.LoadContent -> loadContentAndProcessTasks()
        }
    }

    private fun loadContentAndProcessTasks() {
        viewModelScope.launch {
            // Set initial loading state
            _uiState.update { it.copy(isLoading = true) }

            // Fetch web content
            when (val result = fetchWebContentUseCase()) {
                is NetworkResult.Success -> {
                    val content = result.data
                    _uiState.update { it.copy(isLoading = false, error = null) }

                    launch { getCharacterAtPosition(content) }

                    launch { getEveryFifteenthCharacters(content) }

                    launch { getWordCountOccurences(content) }
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
        val extractedCharacters = extractCharactersByIntervalUseCase(content, 15, 10)
        val taskResult = CharactersListResult(
            title = R.string.task2_title,
            chunkedCharacters = extractedCharacters
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
                taskResults = (it.taskResults + taskResult).toImmutableList(),
            )
        }
    }
}
