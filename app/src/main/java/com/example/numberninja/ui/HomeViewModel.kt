package com.example.numberninja.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeScreenUiState())
    val homeUiState: StateFlow<HomeScreenUiState> = _homeUiState.asStateFlow()

    var userInput by mutableStateOf("")

    // Updates the user input with validation
    fun updateUserInput(guessedWord: String) {
        userInput = guessedWord
    }


    // Resetting the app on startup
    init {
        resetGame()
    }

    /*
     * Re-initializes the game data to restart the game.
     */
    fun resetGame() {
        _homeUiState.value = HomeScreenUiState(currentProblem = generateRandomEquation())
    }

    private fun generateRandomNumber(): Int {
        return (1..9).random()
    }

    private fun generateRandomOperator(): Char {
        val operators = listOf('+', '-', 'x')
        val randomIndex = (operators.indices).random()
        return operators[randomIndex]
    }

    private fun generateRandomEquation(): String {
        val number1 = generateRandomNumber() // Generate new numbers and operator each time
        val operator = generateRandomOperator()
        var number2 = generateRandomNumber()

        // Adjust logic for subtraction and division
        if (operator == '-') {
            while (number1 < number2) {
                number2 = generateRandomNumber()
            }
        }


        return "$number1 $operator $number2"
    }

    private val maxScore = 100
    fun newProblem() {
            val newProblemValue = generateRandomEquation()
            _homeUiState.update { currentState ->
                currentState.copy(
                    currentProblem = newProblemValue,
                    isGuessWrong = false
                    )
            }
    }



    private fun scoreIncrement() {
        val scoreIncrement = 20
        _homeUiState.update { currentState ->
            currentState.copy(currentScore = currentState.currentScore + scoreIncrement)
        }
    }


    private fun validateAnswerAndUpdateUiState(
        generatedNumber1: Int,
        generatedNumber2: Int,
        generatedOperator: Char,
        userAnswer: Int,
    ): Boolean {
        var isGuessWrong = false // Initialize as false

        val correctAnswer = when (generatedOperator) {
            '+' -> generatedNumber1 + generatedNumber2
            '-' -> generatedNumber1 - generatedNumber2
            'x' -> generatedNumber1 * generatedNumber2
            else -> {
                isGuessWrong = true // Handle invalid operator
                0 // Return a dummy value to ensure consistent type
            }
        }

        // Compare userAnswer with correctAnswer (only if not already set to true)
        if (!isGuessWrong) {
            isGuessWrong = userAnswer != correctAnswer
        }

        return isGuessWrong
    }


    private var answer by mutableStateOf(false)


    private fun parseEquation(uiState: HomeScreenUiState): Triple<Int, Char, Int> {
        val currentProblemString = uiState.currentProblem
        val splitEquation = currentProblemString.split(" ")
        if (splitEquation.size != 3)
            throw IllegalArgumentException("Invalid equation format: $currentProblemString")
        val number1 = splitEquation[0].toInt()
        val operator = splitEquation[1].single()
        val number2 = splitEquation[2].toInt()
        return Triple(
            number1,
            operator,
            number2
        )
    }

    fun checkUserGuess() {
        val parsedEquation = parseEquation(homeUiState.value) // Parse equation from current state
        answer = validateAnswerAndUpdateUiState(
            parsedEquation.first,
            parsedEquation.third,
            parsedEquation.second,
            userInput.toIntOrNull() ?: 0 // Handle invalid input gracefully
        )

        if (!answer) { // Proceed if guess is correct
            scoreIncrement()
            val currentScore = _homeUiState.value.currentScore
            if (currentScore == maxScore) {
                _homeUiState.update { currentState ->
                    currentState.copy(
                        isGameOver = true,
                    )
                }
            } else {
                newProblem() // Generate a new equation
            }


        } else {
            // User's guess is wrong, show an error
            _homeUiState.update { currentState ->
                currentState.copy(isGuessWrong = true)
            }
            updateUserInput("")
        }

        updateUserInput("")
    }
}


