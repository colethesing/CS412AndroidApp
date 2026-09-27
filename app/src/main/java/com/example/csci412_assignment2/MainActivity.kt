package com.example.csci412_assignment2

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.csci412_assignment2.ui.theme.CSCI412_Assignment2Theme

class MainActivity : ComponentActivity() {

    private var myService: MyForegroundService? = null
    private var isBound = false
    private val gradeState = mutableStateOf("")
    private val myReceiver = MyBroadcastReceiver()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyForegroundService.LocalBinder
            myService = binder.getService()
            isBound = true
            gradeState.value = myService?.getMyGrade() ?: ""
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            myService = null
        }
    }

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            CSCI412_Assignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        name = "Cole Thesing",
                        id = "1446803",
                        grade = gradeState.value,
                        modifier = Modifier.padding(innerPadding),
                        onExplicitClick = {
                            startActivity(Intent(this, SecondActivity::class.java))
                        },
                        onImplicitClick = {
                            val intent = Intent("com.example.csci412_assignment2.SECOND_ACTIVITY")
                            intent.addCategory(Intent.CATEGORY_DEFAULT)
                            startActivity(intent)
                        },
                        onStartServiceClick = {
                            val intent = Intent(this, MyForegroundService::class.java)
                            ContextCompat.startForegroundService(this, intent)
                        },
                        onBindServiceClick = {
                            val intent = Intent(this, MyForegroundService::class.java)
                            bindService(intent, connection, Context.BIND_AUTO_CREATE)
                        },
                        onSendBroadcastClick = {
                            val intent = Intent("com.example.MY_ACTION")
                            intent.setPackage(packageName)
                            sendBroadcast(intent)
                        }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("com.example.MY_ACTION")
        ContextCompat.registerReceiver(
            this,
            myReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myReceiver)
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}

@Composable
fun MainScreen(
    name: String,
    id: String,
    grade: String,
    modifier: Modifier = Modifier,
    onExplicitClick: () -> Unit = {},
    onImplicitClick: () -> Unit = {},
    onStartServiceClick: () -> Unit = {},
    onBindServiceClick: () -> Unit = {},
    onSendBroadcastClick: () -> Unit = {}
) {
    Surface(color = Color.White, modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Name: $name")
            Text(text = "Student ID: $id")

            Column(modifier = Modifier.padding(top = 32.dp)) {
                Button(onClick = onExplicitClick) { Text("Start Activity Explicitly") }
                Button(onClick = onImplicitClick, modifier = Modifier.padding(top = 12.dp)) {
                    Text("Start Activity Implicitly")
                }
                Button(onClick = onStartServiceClick, modifier = Modifier.padding(top = 12.dp)) {
                    Text("Start Service")
                }
                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = onBindServiceClick) { Text("Bind Service") }
                    Text(text = grade, modifier = Modifier.padding(start = 12.dp))
                }
                Button(onClick = onSendBroadcastClick, modifier = Modifier.padding(top = 12.dp)) {
                    Text("Send Broadcast")
                }
            }
        }
    }
}