package com.example.truecallerassignment.presentation

/**
 * Sealed class representing UI events
 * Events flow up from the UI to the ViewModel
 */
sealed class MainUiEvent {
    object LoadContent : MainUiEvent()
}