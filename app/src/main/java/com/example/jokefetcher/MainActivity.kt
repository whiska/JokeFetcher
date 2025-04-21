package com.example.jokefetcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setContent {
                MaterialTheme {
                    JokeScreen()
                }
            }
        }
    }
}

@Composable
fun JokeScreen() {
    var joke by remember { mutableStateOf("Press for a laugh") }
    var buttonClicked by remember { mutableStateOf(false) }
    var imageRes by remember { mutableStateOf(R.drawable.baseline_child_care_24) }
    val scope = rememberCoroutineScope()
    val buttonColor by animateColorAsState(
        if (buttonClicked) Color(colorResource(R.color.purple_500).toArgb())
        else Color(colorResource(R.color.purple_700).toArgb())
    )
    val imageList = listOf(
        R.drawable.baseline_child_care_24,
        R.drawable.baseline_add_reaction_24,
        R.drawable.baseline_accessibility_new_24
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(colorResource(R.color.white).toArgb()))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Joke Display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = joke,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color(colorResource(R.color.black).toArgb())
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Fancy Button
        Button(
            onClick = {
                buttonClicked = true
                scope.launch {
                    try {
                        val response = JokeService.api.getJoke()
                        joke = response.joke
                        imageRes = imageList.random()
                    } catch (e: Exception) {
                        joke = "No laughs today—try again!"
                        imageRes = imageList.random()
                    }
                    buttonClicked = false
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Text(
                text = "Fetch Joke",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Laughing Dad Joke Image",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}