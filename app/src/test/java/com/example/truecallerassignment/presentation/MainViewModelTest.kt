import com.example.truecallerassignment.CoroutineTestRule
import com.example.truecallerassignment.data.remote.NetworkResult
import com.example.truecallerassignment.domain.model.CharacterResult
import com.example.truecallerassignment.domain.model.CharactersListResult
import com.example.truecallerassignment.domain.model.WordFrequencyResult
import com.example.truecallerassignment.domain.usecase.CountWordOccurrencesUseCase
import com.example.truecallerassignment.domain.usecase.ExtractCharactersByIntervalUseCase
import com.example.truecallerassignment.domain.usecase.FetchWebContentUseCase
import com.example.truecallerassignment.domain.usecase.GetCharacterAtPositionUseCase
import com.example.truecallerassignment.presentation.MainUiEvent
import com.example.truecallerassignment.presentation.MainUiState
import com.example.truecallerassignment.presentation.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()


    // Use mockk for all dependencies
    private lateinit var fetchWebContentUseCase: FetchWebContentUseCase
    private lateinit var getCharacterAtPositionUseCase: GetCharacterAtPositionUseCase
    private lateinit var extractCharactersByIntervalUseCase: ExtractCharactersByIntervalUseCase
    private lateinit var countWordOccurrencesUseCase: CountWordOccurrencesUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {

        // Init mocks
        fetchWebContentUseCase = mockk()
        getCharacterAtPositionUseCase = mockk()
        extractCharactersByIntervalUseCase = mockk()
        countWordOccurrencesUseCase = mockk()

        viewModel = MainViewModel(
            fetchWebContentUseCase,
            getCharacterAtPositionUseCase,
            extractCharactersByIntervalUseCase,
            countWordOccurrencesUseCase
        )
    }

    @Test
    fun `loadContentAndProcessTasks success updates uiState with all results`() = runTest {
        val fakeContent = "this is some test content"

        coEvery { fetchWebContentUseCase.invoke() } returns NetworkResult.Success(data = fakeContent)
        coEvery { getCharacterAtPositionUseCase.invoke(fakeContent, 14) } returns 't'
        coEvery {
            extractCharactersByIntervalUseCase.invoke(
                fakeContent,
                15,
                10
            )
        } returns listOf(listOf('t'))
        coEvery { countWordOccurrencesUseCase.invoke(fakeContent) } returns mapOf("this" to 1)

        viewModel.onEvent(MainUiEvent.LoadContent)

        advanceUntilIdle() // Wait for all coroutines

        val uiState = viewModel.uiState.value

        assert(!uiState.isLoading)
        assert(uiState.error == null)

        val taskResults = uiState.taskResults
        assert(taskResults.any { it is CharacterResult && it.character == 't' })
        assert(taskResults.any {
            it is CharactersListResult && it.chunkedCharacters[0].contains('t')
        })
        assert(taskResults.any { it is WordFrequencyResult && it.wordCounts["this"] == 1 })
    }

    @Test
    fun `loadContentAndProcessTasks failure updates uiState with error`() = runTest {
        val errorMessage = "Error fetching content"
        coEvery { fetchWebContentUseCase.invoke() } returns NetworkResult.Error(errorMessage)

        viewModel.onEvent(MainUiEvent.LoadContent)

        advanceUntilIdle()

        val uiState = viewModel.uiState.value

        assert(!uiState.isLoading)
        assert(uiState.error == errorMessage)
        assert(uiState.taskResults.isEmpty())
    }

    @Test
    fun `loadContentAndProcessTasks completes all tasks and updates state`() = runTest {
        // Prepare mocks
        val sampleContent = "The quick brown fox jumps over the lazy dog"
        coEvery { fetchWebContentUseCase() } returns NetworkResult.Success(sampleContent)
        coEvery { getCharacterAtPositionUseCase(sampleContent, 14) } returns 'o'
        coEvery { extractCharactersByIntervalUseCase(sampleContent, 15, 10) } returns listOf(
            listOf(
                'o',
                'l'
            )
        )
        coEvery { countWordOccurrencesUseCase(sampleContent) } returns mapOf(
            "the" to 2,
            "quick" to 1
        )

        // Trigger the event
        viewModel.onEvent(MainUiEvent.LoadContent)
        advanceUntilIdle() // Ensures all coroutines are finished

        // Check UI state for completion
        val taskResults = viewModel.uiState.value.taskResults

        // Assert each result is present as expected
        assertTrue(taskResults.any { it is CharacterResult && it.character == 'o' })
        assertTrue(taskResults.any {
            it is CharactersListResult && it.chunkedCharacters == listOf(
                listOf(
                    'o',
                    'l'
                )
            )
        })
        assertTrue(taskResults.any {
            it is WordFrequencyResult && it.wordCounts == mapOf(
                "the" to 2,
                "quick" to 1
            )
        })
        assertEquals(3, taskResults.size)
    }

    @Test
    fun `tasks complete in order based on their execution time`() = runTest {
        // Given
        val fakeContent = "test content"
        coEvery { fetchWebContentUseCase() } returns NetworkResult.Success(fakeContent)

        coEvery { getCharacterAtPositionUseCase.invoke(any(), any()) } coAnswers {
            delay(40)
            'A'
        }

        coEvery { extractCharactersByIntervalUseCase.invoke(any(), any(), any()) } coAnswers {
            delay(10)
            listOf(listOf('B', 'C'))
        }

        coEvery { countWordOccurrencesUseCase.invoke(any()) } coAnswers {
            delay(70)
            mapOf("test" to 1)
        }

        // Collect all emissions
        val emissions = mutableListOf<MainUiState>()
        val collectJob = backgroundScope.launch {
            viewModel.uiState.collect { state ->
                if (state.taskResults.isNotEmpty()) {
                    emissions.add(state)
                }
            }
        }

        // When
        viewModel.onEvent(MainUiEvent.LoadContent)
        advanceUntilIdle()

        // Then - verify order
        assertEquals(3, emissions.size)

        assertEquals(1, emissions[0].taskResults.size)
        assertTrue(emissions[0].taskResults[0] is CharactersListResult)

        assertEquals(2, emissions[1].taskResults.size)
        assertTrue(emissions[1].taskResults[1] is CharacterResult)

        assertEquals(3, emissions[2].taskResults.size)
        assertTrue(emissions[2].taskResults[2] is WordFrequencyResult)

        collectJob.cancel()
    }
}
