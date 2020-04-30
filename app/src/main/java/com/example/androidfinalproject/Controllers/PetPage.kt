package com.example.androidfinalproject.Controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.activity_pet_page.*

class PetPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_page)
        val username = intent.getStringExtra("Username")
    }
}
