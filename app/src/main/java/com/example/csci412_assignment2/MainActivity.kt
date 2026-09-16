package com.example.csci412_assignment2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.csci412_assignment2.ui.theme.CSCI412_Assignment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSCI412_Assignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        name = "Cole Thesing",
                        id = "1446803",
                        modifier = Modifier.padding(innerPadding),
                        onExplicitClick = {
                            // Explicit intent: names the exact class to launch
                            val intent = Intent(this, SecondActivity::class.java)
                            startActivity(intent)
                        },
                        onImplicitClick = {
                            // Implicit intent: describes an action, system resolves the target
                            val intent = Intent("com.example.csci412_assignment2.SECOND_ACTIVITY")
                            intent.addCategory(Intent.CATEGORY_DEFAULT)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    name: String,
    id: String,
    modifier: Modifier = Modifier,
    onExplicitClick: () -> Unit = {},
    onImplicitClick: () -> Unit = {}
) {
    Surface(color = Color.White, modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Name: $name")
            Text(text = "Student ID: $id")

            Column(modifier = Modifier.padding(top = 32.dp)) {
                Button(onClick = onExplicitClick) {
                    Text("Start Activity Explicitly")
                }
                Button(
                    onClick = onImplicitClick,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text("Start Activity Implicitly")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CSCI412_Assignment2Theme {
        MainScreen(name = "Cole Thesing", id = "1446803")
    }
}