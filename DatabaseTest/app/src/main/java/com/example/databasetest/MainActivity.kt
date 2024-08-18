package com.example.databasetest

import android.os.Bundle
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
        val createbtn: Button = findViewById(R.id.create)
        val helper = MyDataHelper(this, "data", 1)
        createbtn.setOnClickListener {
            helper.writableDatabase
        }
        val addData: Button = findViewById(R.id.addData)
        addData.setOnClickListener {
            val db = helper.writableDatabase
            db.execSQL("insert into books(name, price) values(?, ?)", arrayOf("lhj", 13.2))
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}