package com.example.databasetest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDataHelper(val context: Context, val name: String, val version: Int )
    : SQLiteOpenHelper(context, name, null, version) {
        private val createDatabase = "create table books(" +
                "id integer primary key autoincrement," +
                "name text," +
                "price real)"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createDatabase)
        Toast.makeText(context, "create database success", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}