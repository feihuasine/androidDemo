package com.example.recycleviewtest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initFruits()
        //val layoutManager = LinearLayoutManager(this)
        //layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val layoutManager = StaggeredGridLayoutManager(3,
            StaggeredGridLayoutManager.VERTICAL)
       // val layoutManager = GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        val recycleView: RecyclerView = findViewById(R.id.recycleView)
        recycleView.layoutManager = layoutManager
        val adapter = FruitAdapter(fruitList)
        recycleView.adapter = adapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initFruits() {
        repeat(10) {
            fruitList.add(Fruit(getRandomStringLength("Apple"), R.mipmap.apple_pic))
            fruitList.add(Fruit(getRandomStringLength("Banana"), R.mipmap.banana_pic))
            fruitList.add(Fruit(getRandomStringLength("pear"), R.mipmap.orange_pic))
            fruitList.add(Fruit(getRandomStringLength("watermelon"), R.mipmap.watermelon_pic))
        }
    }

    private fun getRandomStringLength(str: String): String{
        val n = (1..20).random()
        //val n = 1
        val builder = StringBuilder()
        repeat(n) {
            builder.append(str)
        }
        return builder.toString()
    }
}