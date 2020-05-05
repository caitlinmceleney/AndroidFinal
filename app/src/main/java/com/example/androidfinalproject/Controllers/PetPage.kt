package com.example.androidfinalproject.Controllers

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfinalproject.Models.Pet
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.activity_pet_page.*
import java.sql.Types.NULL
import java.util.*


class PetPage : AppCompatActivity() {
var username = ""
var thisPet = Pet("", "NULL", "NULL", 0 , 0,0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_page)
        username = intent.getStringExtra("Username")
        val myDb= PetDatabaseManager(applicationContext)
        myDb.writableDatabase
        myDb.getPet(username)
        thisPet = myDb.getPet(username)


//        val period: Long = 0
//        Timer().schedule(object : TimerTask() {
//            override fun run() { // do your task here
//
//            }
//        }, 0, period)
    }


    }

