package com.example.testapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.first_layout)
        Log.d("MainActivity", "MainAct is $taskId")
        doSomeThing()
        val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener {
//            Toast.makeText(this, "kkk", Toast.LENGTH_SHORT).show()
//            finish()
//            val intent = Intent(this, SecondActivity::class.java)
//            val intent = Intent("com.example.testapp.ACTION_START")
//            intent.addCategory("com.example.testapp.MY_CATEGORY")
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("https://www.baidu.com")
//            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:10086")
//            val str = "hello,secondActivity"
//            val intent = Intent(this, SecondActivity::class.java)
//            intent.putExtra("extra-data", str)
//            val intent = Intent(this, SecondActivity::class.java)
//            launcher.launch(intent)

//            val intent = Intent(this, SecondActivity::class.java)
//            startActivity(intent)
//            SecondActivity.actionStart(this, "lll", "kkk")
//            val progress_bar : ProgressBar = findViewById(R.id.progress_bar)
//            progress_bar.progress += 10;
            AlertDialog.Builder(this).apply {
                setTitle("This is Dialog")
                setMessage("Something important.")
                setCancelable(false)
                setPositiveButton("OK") {
                    dialog, which ->
                }
                setNegativeButton("Cancel") {
                    dialog, which ->
                }
                show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.first)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private val launcher  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val str = result.data?.getStringExtra("msg")
            if (str != null) {
                Log.d("MainActivity", str)
            }
        }

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity", this.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_item -> Toast.makeText(this, "click add", Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "click remove", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    
}