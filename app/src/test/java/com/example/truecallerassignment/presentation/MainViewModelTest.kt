import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import com.example.truecallerassignment.presentation.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @JvmField
    @Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

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
}
