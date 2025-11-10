package com.example.truecallerassignment.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.truecallerassignment.domain.model.CharacterResult
import com.example.truecallerassignment.domain.model.CharactersListResult
import com.example.truecallerassignment.domain.model.WordFrequencyResult
import com.example.truecallerassignment.domain.model.TaskResult
import com.example.truecallerassignment.presentation.components.CharacterContent
import com.example.truecallerassignment.presentation.components.CharactersListContent
import com.example.truecallerassignment.presentation.components.ErrorCard
import com.example.truecallerassignment.presentation.components.LoadButton
import com.example.truecallerassignment.presentation.components.TaskCard
import com.example.truecallerassignment.presentation.components.WordFrequencyContent
import com.truecaller.task.R

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.main_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.main_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            LoadButton(
                onClick = { viewModel.onEvent(MainUiEvent.LoadContent) },
                isLoading = uiState.isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            uiState.error?.let { error ->
                ErrorCard(message = error)
                Spacer(modifier = Modifier.height(16.dp))
            }

            TaskResultList(uiState.taskResults)

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun TaskResultList(taskResults: List<TaskResult>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        taskResults.forEach { state ->
            TaskCard(
                title = stringResource(state.title),
                isLoading = false,
                isCompleted = true,
            ) {
                when (state) {
                    is CharacterResult -> state.character?.let { CharacterContent(it) }
                    is CharactersListResult -> CharactersListContent(state.characters)
                    is WordFrequencyResult -> WordFrequencyContent(state.wordCounts)
                }
            }
        }
    }
}



