package com.example.csci412_assignment2

import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.csci412_assignment2.ui.theme.CSCI412_Assignment2Theme
class ThirdActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSCI412_Assignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ImageCaptureScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ImageCaptureScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var capturedImage by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedImage = bitmap
        } else {
            Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            try {
                cameraLauncher.launch(null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No camera app found", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Capture Image")
        }

        capturedImage?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Captured image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }
    }
}