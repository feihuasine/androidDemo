package com.example.sharedpreferencetest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.save)
        button.setOnClickListener {
            val editor = getSharedPreferences("data", MODE_PRIVATE).edit()
            editor.apply {
                putInt("age", 26)
                putString("name", "lhj")
                putBoolean("sex", true)
            }.apply()
        }

        val readData: Button = findViewById(R.id.readData)
        readData.setOnClickListener {
            val reader = getSharedPreferences("data", MODE_PRIVATE)
            val age = reader.getInt("age", 0)
            val name = reader.getString("name", "" )
            val sex = reader.getBoolean("sex", false)
            Log.d("lhj", "$age : $name : $sex")
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}