package com.example.truecallerassignment.domain.usecase

import com.example.truecallerassignment.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExtractCharactersByIntervalUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var useCase: ExtractCharactersByIntervalUseCase

    @Before
    fun setup() {
        useCase = ExtractCharactersByIntervalUseCase()
    }

    @Test
    fun `returns every nth character correctly`() = runTest {
        val content = "abcdefghij"
        val interval = 2
        val elementsPerChunk = 5
        val expected = listOf(listOf('b', 'd', 'f', 'h', 'j')) // indexes 1,3,5,7,9 (0-based)
        val result = useCase(content, interval, elementsPerChunk)
        assertEquals(expected, result)
    }

    @Test
    fun `returns empty list when interval is greater than length`() = runTest {
        val content = "abc"
        val interval = 10
        val elementsPerChunk = 5
        val result = useCase(content, interval, elementsPerChunk)
        assert(result.isEmpty())
    }

    @Test
    fun `returns correct for interval 1 (all chars)`() = runTest {
        val content = "abc"
        val interval = 1
        val elementsPerChunk = 5
        val expected = listOf(listOf('a', 'b', 'c'))
        val result = useCase(content, interval, elementsPerChunk)
        assertEquals(expected, result)
    }
}
