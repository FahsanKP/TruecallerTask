package com.example.truecallerassignment.domain.usecase

import javax.inject.Inject

class GetCharacterAtPositionUseCase @Inject constructor() {

    operator fun invoke(content: String, position: Int): Char? {
        return content.getOrNull(position)
    }
}
