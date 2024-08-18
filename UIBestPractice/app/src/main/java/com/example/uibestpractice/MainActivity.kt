package com.example.uibestpractice

import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnClickListener {

    private val msgList = ArrayList<Msg>()
    private var adapter: MsgAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initMsg()
        val layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = layoutManager
        adapter = MsgAdapter(msgList)
        recyclerView.adapter = adapter
        val send:Button = findViewById(R.id.send)
        send.setOnClickListener(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(v: View?) {
        val send: Button = findViewById(R.id.send)
        val inputText: EditText = findViewById(R.id.inputText)
        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        when (v) {
            send -> {
                val content = inputText.text.toString()
                if (content.isNotEmpty()) {
                    val msg = Msg(content, Msg.TYPE_SENT)
                    msgList.add(msg)
                    adapter?.notifyItemInserted(msgList.size - 1)
                    recyclerView.scrollToPosition(msgList.size - 1)
                    inputText.setText("")
                }
            }
        }
    }

    private fun initMsg() {
        val msg = Msg("hello, guy", Msg.TYPE_RECEIVED)
        msgList.add(msg)
        val msg2 = Msg("hello, who is that?", Msg.TYPE_SENT)
        msgList.add(msg2)
        val msg3 = Msg("Nice to me to", Msg.TYPE_RECEIVED)
        msgList.add(msg3)
    }
}