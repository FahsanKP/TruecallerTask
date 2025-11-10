package com.example.truecallerassignment.domain.usecase

import com.example.truecallerassignment.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountWordOccurrencesUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var useCase: CountWordOccurrencesUseCase

    @Before
    fun setup() {
        useCase = CountWordOccurrencesUseCase()
    }

    @Test
    fun `counts word occurrences correctly`() = runTest {
        val content = "one two two three three three"
        val expected = mapOf(
            "three" to 3,
            "two" to 2,
            "one" to 1
        )
        val result = useCase(content)
        assertEquals(expected, result)
    }

    @Test
    fun `counts words case insensitively`() = runTest {
        val content = "Word word WORD two WoRd"
        val expected = mapOf(
            "word" to 4,
            "two" to 1
        )
        val result = useCase(content)
        assertEquals(expected, result)
    }

    @Test
    fun `returns empty map for empty or whitespace input`() = runTest {
        val result = useCase(" \t\n")
        assertEquals(emptyMap<String, Int>(), result)
    }

    @Test
    fun `splits words on different whitespace`() = runTest {
        val content = "a\tb\nc   a"
        val expected = mapOf(
            "a" to 2,
            "b" to 1,
            "c" to 1
        )
        val result = useCase(content)
        assertEquals(expected, result)
    }
}
