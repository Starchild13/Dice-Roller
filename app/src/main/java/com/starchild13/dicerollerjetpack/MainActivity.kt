
package com.starchild13.dicerollerjetpack

// Import statements for various Android and Jetpack Compose functionalities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starchild13.dicerollerjetpack.ui.theme.DiceRollerJetpackTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Main activity class, which inherits from ComponentActivity (a Jetpack Compose activity)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting the content of this activity to be DiceRollerApp wrapped in DiceRollerJetpackTheme
        setContent {
            DiceRollerJetpackTheme {
                DiceRollerApp()
            }
        }
    }
}

@Preview  // This annotation allows you to preview the composable in Android Studio
@Composable  // Composable function to create the UI
fun DiceRollerApp() {
    // This calls another composable, DiceWithButtonAndImage, to build the UI
    DiceWithButtonAndImage()
}

@Composable  // Composable function to create a part of the UI
fun DiceWithButtonAndImage(modifier: Modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
    var result by remember { mutableStateOf(1) }  // Holds the result of the dice roll
    var rolling by remember { mutableStateOf(false) }  // Tracks if a dice roll is in progress

    // Determines the image resource to use based on the dice roll result
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    // Animates the rotation of the dice image while a roll is in progress
    val rotationAngle: Float by animateFloatAsState(
        targetValue = if (rolling) 1080f else 0f,  // Target rotation angle: 1080 degrees when rolling, 0 otherwise
        animationSpec = tween(durationMillis = 1000)  // Specifies the animation to take 1 second
    )

    // Creates a CoroutineScope tied to this composable
    val coroutineScope = rememberCoroutineScope()

    // Arranges its children vertically
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally  // Centers children horizontally
    ) {
        // Displays the dice image
        Image(
            painter = painterResource(id = imageResource),  // Assigning a Painter object from a resource ID obtained through imageResource to the painter property
            contentDescription = result.toString(),  // Setting the contentDescription property to the string representation of result

            modifier = Modifier
                .padding(top = 16.dp)  // Adds padding at the top
                .rotate(rotationAngle)  // Applies rotation animation
        )

        Spacer(modifier = Modifier.height(16.dp))  // Adds spacing between the image and the button

        // Button to trigger a dice roll
        Button(
            onClick = {
                if (!rolling) {  // Prevents triggering a new roll while one is already in progress
                    rolling = true  // Sets the rolling state to true
                    coroutineScope.launch {  // Launches a coroutine
                        delay(1000)  // Waits for 1 second (for the rotation animation to complete)
                        result = (1..6).random()  // Generates a new random dice roll result
                        rolling = false  // Sets the rolling state back to false
                    }
                }
            }
        ) {
            // Button's text
            Text(stringResource(R.string.roll), fontSize = 18.sp)
        }
    }
}










