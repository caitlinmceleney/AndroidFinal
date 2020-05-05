package com.example.androidfinalproject.Controllers

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfinalproject.Models.Pet
import com.example.androidfinalproject.R
import java.util.*


class PetPage : AppCompatActivity() {
var username = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_page)
        username = intent.getStringExtra("Username")
        var myPetAsync = getPetAsync()
        myPetAsync.execute(username)


        val period: Long = 0
        Timer().schedule(object : TimerTask() {
            override fun run() { // do your task here

            }
        }, 0, period)
    }

    inner class getPetAsync: AsyncTask<String, Unit?, Unit?>() {

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            //set up pet info here
        }

        override fun doInBackground(vararg params: String) {
            val myDb= PetDatabaseManager(applicationContext)
            myDb.writableDatabase
            myDb.getPet(params[0])
            return
        }
    }
}
