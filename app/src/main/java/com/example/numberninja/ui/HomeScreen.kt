package com.example.numberninja.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numberninja.R
import com.example.numberninja.ui.theme.CustomShapes
import com.example.numberninja.ui.theme.NumberNinjaTheme


@Composable
fun HomeScreen(modifier: Modifier = Modifier,homeViewModel: HomeViewModel = viewModel()) {
    val homeUiState by homeViewModel.homeUiState.collectAsState()

    Column(
        modifier = modifier
            .padding(5.dp)
            .fillMaxSize(),

        ) {

        Text(
            text = stringResource(id = R.string.home_name),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.lemon_regular)),
            modifier = modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(22.dp),
        ) {


            Score(modifier = Modifier, score = homeUiState.currentScore)

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f),


                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(10.dp),
                shape = CustomShapes.small,
                border = BorderStroke(2.dp, (MaterialTheme.colorScheme.primary)),

                ) {

                Text(
                    text = stringResource(id = R.string.intro_head),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    modifier = modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = stringResource(id = R.string.intro_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily.SansSerif,
                    modifier = modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                RandomProblem(currentProblem = homeUiState.currentProblem)
                UserTextField(
                    userInput = homeViewModel.userInput,
                    userEdit = { homeViewModel.updateUserInput(it) },
                    isGuessWrong = homeUiState.isGuessWrong,
                    onKeyboardDone = { homeViewModel.checkUserGuess()}
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { homeViewModel.checkUserGuess()},
                shape = CustomShapes.large
            ) {
                Text(
                    text = stringResource(id = R.string.submit_button),
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = { homeViewModel.newProblem() },
                modifier = Modifier.fillMaxWidth(),
                shape = CustomShapes.large
            ) {
                Text(
                    text = stringResource(id = R.string.skip_button),
                    fontSize = 16.sp
                )
            }

            if (homeUiState.isGameOver) {
                CongratulationCard(
                    score = homeUiState.currentScore,
                    onPlayAgain = { homeViewModel.resetGame() }
                )

            }


        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun Show() {
    NumberNinjaTheme {
        HomeScreen()
    }
}




