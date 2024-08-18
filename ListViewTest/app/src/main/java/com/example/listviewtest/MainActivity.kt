package com.example.listviewtest

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
//    val datas = listOf("apple", "pear", "banana", "watermelon", "Mango", "Strawberry", "Cherry",
//        "apple", "pear", "banana", "watermelon", "Mango", "Strawberry","apple", "pear", "banana", "watermelon", "Mango", "Strawberry", "Cherry",
//        "apple", "pear", "banana", "watermelon", "Mango", "Strawberry")
    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas)
//        val lst: ListView = findViewById(R.id.lstV)
//        lst.adapter = adapter
        initFruit()

        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)
        val lst: ListView = findViewById(R.id.lstV)
        lst.adapter = adapter
        lst.setOnItemClickListener { parent, view, position, id ->
            val fruit = fruitList[position]
            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initFruit() {
        repeat (10) {
            fruitList.add(Fruit("apple", R.mipmap.apple_pic))
            fruitList.add(Fruit("pear", R.mipmap.pear_pic))
            fruitList.add(Fruit("banana", R.mipmap.banana_pic))

        }
    }
}