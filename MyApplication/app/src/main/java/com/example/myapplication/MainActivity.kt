package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SimpleWidgetColumn(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleWidgetColumn(modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "This is Text",
            color = Color.Blue,
            fontSize = 26.sp,
            modifier = modifier
        )
        val context = LocalContext.current
        Button(onClick = { Toast.makeText(context, "This is toast", Toast.LENGTH_SHORT).show() }) {
            Text(
                text = "This is Button",
                color = Color.White,
                fontSize = 26.sp
                )
        }
        
        TextField(value = "",
            onValueChange = {},
            placeholder = { Text(text = "Type anything")},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            )
            )
    }
}

@Composable
@Preview
fun SimplePreview() {
    SimpleWidgetColumn()
}

