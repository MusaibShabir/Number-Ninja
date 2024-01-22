package com.example.numberninja.ui

data class HomeScreenUiState(
    val currentProblem: String = "",
    val userInput: String = "",
    var currentScore: Int = 0,
    val isGuessWrong: Boolean = false,
    val isGameOver: Boolean = false

)



