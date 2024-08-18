package com.example.testapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        Log.d("SecondActivity", "secondAct is $taskId")
//        val extraStr = intent.getStringExtra("extra-data")
//        Log.d("SecondActivity","data is $extraStr")
//        val intent = Intent()
//        intent.putExtra("msg", "hello,FirstActivity")
//        setResult(RESULT_OK, intent)
//        finish()
        val getParm1 = intent.getStringExtra("param1")
        val getParm2 = intent.getStringExtra("param2")

        if (getParm1 != null) {
            Log.d("SecondAct", getParm1)
        }

        if (getParm2 != null) {
            Log.d("SecondAct", getParm2)
        }

        val button2 : Button = findViewById(R.id.button2)
        button2.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.second)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        @JvmStatic
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("param1", data1)
            intent.putExtra("param2", data2)
            context.startActivity((intent))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SecondActivity", this.toString())
    }
}