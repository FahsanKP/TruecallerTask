package com.example.truecallerassignment.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetCharacterAtPositionUseCaseTest {

    private lateinit var useCase: GetCharacterAtPositionUseCase

    @Before
    fun setup() {
        useCase = GetCharacterAtPositionUseCase()
    }

    @Test
    fun `returns the character at the given position`() {
        val content = "HelloWorld"
        val position = 4
        val expectedChar = 'o'
        val result = useCase(content, position)
        assertEquals(expectedChar, result)
    }

    @Test
    fun `returns null if position is out of bounds (negative)`() {
        val content = "HelloWorld"
        val position = -1
        val result = useCase(content, position)
        assertNull(result)
    }

    @Test
    fun `returns null if position is out of bounds (too large)`() {
        val content = "HelloWorld"
        val position = 100
        val result = useCase(content, position)
        assertNull(result)
    }
}
