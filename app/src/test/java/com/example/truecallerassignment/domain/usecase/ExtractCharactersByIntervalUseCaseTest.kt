package com.example.truecallerassignment.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExtractCharactersByIntervalUseCaseTest {

    private lateinit var useCase: ExtractCharactersByIntervalUseCase

    @Before
    fun setup() {
        useCase = ExtractCharactersByIntervalUseCase()
    }

    @Test
    fun `returns every nth character correctly`() {
        val content = "abcdefghij"
        val interval = 2
        val expected = listOf('b', 'd', 'f', 'h', 'j') // indexes 1,3,5,7,9 (0-based)
        val result = useCase(content, interval)
        assertEquals(expected, result)
    }

    @Test
    fun `returns empty list when interval is greater than length`() {
        val content = "abc"
        val interval = 10
        val expected = emptyList<Char>()
        val result = useCase(content, interval)
        assertEquals(expected, result)
    }

    @Test
    fun `returns correct for interval 1 (all chars)`() {
        val content = "abc"
        val interval = 1
        val expected = listOf('a', 'b', 'c')
        val result = useCase(content, interval)
        assertEquals(expected, result)
    }
}
