package com.example.fragmenttest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
//        button.setOnClickListener {
//            replaceFrg(AnotherFragment())
//        }
//        replaceFrg(RightFragment())
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
//
//    private fun replaceFrg(fragment : Fragment) {
//        val fragmentManager = supportFragmentManager
//        val transition = fragmentManager.beginTransaction()
//        transition.replace(R.id.rightLayout, fragment)
//        transition.addToBackStack(null)
//        transition.commit()
//    }
}