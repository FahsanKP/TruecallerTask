import com.example.truecallerassignment.data.remote.NetworkResult
import com.example.truecallerassignment.data.remote.api.WebContentApi
import com.example.truecallerassignment.data.repository.WebContentRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WebContentRepositoryImplTest {

    private lateinit var api: WebContentApi
    private lateinit var repository: WebContentRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        repository = WebContentRepositoryImpl(api)
    }

    @Test
    fun `fetchWebContent returns success result from api`() = runTest {
        val expected = NetworkResult.Success("Test content")

        coEvery { api.fetchWebContent() } returns expected

        val result = repository.fetchWebContent()
        assertEquals(expected, result)
    }

    @Test
    fun `fetchWebContent returns error result from api`() = runTest {
        val expected = NetworkResult.Error("Something went wrong")

        coEvery { api.fetchWebContent() } returns expected

        val result = repository.fetchWebContent()
        assertEquals(expected, result)
    }
}
