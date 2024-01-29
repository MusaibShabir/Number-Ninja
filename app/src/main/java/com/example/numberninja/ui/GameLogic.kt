package com.example.numberninja.ui

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberninja.R
import com.example.numberninja.ui.theme.CustomShapes

@Composable
fun RandomProblem(
    modifier: Modifier = Modifier,
    currentProblem: String,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)
        .background(Color.LightGray)
    ) {
        Row(
            modifier = modifier
                // apply centering modifier to Row
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentProblem,
                fontSize = 40.sp,
            )
        }
    }
}


@Composable
fun UserTextField(
    userInput: String,
    userEdit: (String) -> Unit,
    isGuessWrong: Boolean,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = userInput, // Access value using .value
            singleLine = true,
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = userEdit, // Pass newText to userEdit
            label = {
                if (isGuessWrong) {
                    Text(stringResource(R.string.wrong_guess))
                } else {
                    Text(stringResource(R.string.enter_your_answer))
                }
            },
            isError = isGuessWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            )
        )
    }

}

@Composable
fun Score(modifier: Modifier, score: Int) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(.1f)
            .padding(bottom = 10.dp),

        shape = CustomShapes.medium,
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(2.dp, (MaterialTheme.colorScheme.primary)),
    ) {
        Box (
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ){
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Text(
                    text = stringResource(id = R.string.game_score_head),
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .padding(start = 10.dp),
                )

                Text(
                    text = "$score",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .padding(end = 10.dp),
                )
            }
        }

    }
}


@Composable
fun CongratulationCard(
        score: Int,
        onPlayAgain: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        val activity = (LocalContext.current as Activity)

        AlertDialog(
            onDismissRequest = {
            },
            title = { Text(text = stringResource(R.string.congratulations)) },
            text = { Text(text = stringResource(R.string.you_scored, score)) },
            modifier = modifier,
            dismissButton = {
                TextButton(
                    onClick = {
                        activity.finish()
                    }
                ) {
                    Text(text = stringResource(R.string.exit))
                }
            },
            confirmButton = {
                TextButton(onClick = onPlayAgain) {
                    Text(text = stringResource(R.string.play_again))
                }
            }
        )
    }



