package com.example.testapp

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class TitleLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.title, this)
        val backBtn: Button = findViewById(R.id.backBtn)
        val editBtn: Button = findViewById(R.id.editBtn)

        backBtn.setOnClickListener {
            val activity = context as Activity
            activity.finish()
        }

        editBtn.setOnClickListener {
            Toast.makeText(context, "you click edit button", Toast.LENGTH_SHORT).show()
        }
    }
}