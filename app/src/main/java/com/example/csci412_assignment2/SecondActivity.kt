package com.example.csci412_assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.csci412_assignment2.ui.theme.CSCI412_Assignment2Theme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSCI412_Assignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SecondScreen(
                        modifier = Modifier.padding(innerPadding),
                        onMainClick = { finish() } // returns to MainActivity
                    )
                }
            }
        }
    }
}

@Composable
fun SecondScreen(modifier: Modifier = Modifier, onMainClick: () -> Unit = {}) {
    val challenges = listOf(
        "1. Fragmentation across devices, screen sizes, and OS versions",
        "2. Managing battery and memory constraints",
        "3. Handling intermittent or no network connectivity",
        "4. Ensuring data security and user privacy",
        "5. Coordinating asynchronous operations and background tasks",
        "6. Maintaining consistent UI/UX across platforms"
    )

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Mobile Software Engineering Challenges")
            challenges.forEach { challenge ->
                Text(text = challenge, modifier = Modifier.padding(top = 8.dp))
            }
            Button(
                onClick = onMainClick,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text("Main Activity")
            }
        }
    }
}