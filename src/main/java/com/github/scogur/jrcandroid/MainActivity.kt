package com.github.scogur.jrcandroid

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.*
import java.util.Objects

class MainActivity : ComponentActivity() {
    var ip: String = "192.168.55.228"
    var port: Int = 8000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var client = Client()
        setContent {
            Column {
                ServerConnection(client)
                CommandLine(client)
                ResultBox(text = getText(client))
            }
        }
    }
}

@Composable
fun ServerConnection(client: Client){
    var ip by remember { mutableStateOf("192.168.55.228") }
    var port by remember { mutableStateOf("8000") }
    val context = LocalContext.current;

    Column {
        Row {
            Column {
                Text(text = "IP")
                TextField(value = ip, onValueChange = { ip = it })
            }
            Column {
                Text(text = "Port")
                TextField(value = port, onValueChange = { port = it })
            }
        }


        Button(onClick = {
            connectToServer(client, ip, port.toInt())
            //if (client.isConnected) {makeToast(context = context, text = "Connected")}
        }) {
            Text(text = "Connect")
        }

    }

}


fun makeToast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}


@Composable
fun CommandLine(client: Client){
    var command by remember { mutableStateOf("") }

    Column {
        TextField(value = command, onValueChange = { command = it })

        Button(onClick = { CoroutineScope(Dispatchers.IO).launch { client.sendMessage(command) } }) {
            Text(text = "send")
        }
    }
}

@Composable
fun ResultBox(text: String){
    Text{text = text}
}

fun connectToServer(client: Client, ip: String, port: Int){
    CoroutineScope(Dispatchers.IO).launch {
        client.startConnection(ip, port)

    }

}

fun getText(client: Client):String {
    return "pee"
}