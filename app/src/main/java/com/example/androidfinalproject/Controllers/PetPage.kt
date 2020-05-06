package com.example.androidfinalproject.Controllers

import android.animation.ValueAnimator.INFINITE
import android.annotation.SuppressLint
import android.graphics.LinearGradient
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidfinalproject.Models.Pet
import com.example.androidfinalproject.R

import kotlinx.android.synthetic.main.activity_pet_page.*
import java.util.*
import kotlin.concurrent.timerTask


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


        petNameTxt.text = thisPet.getName()
        happinessProgress.progress = thisPet.getHappiness()
        hungerProgress.progress = thisPet.getHunger()
        cleanlinessProgress.progress = thisPet.getCleanliness()

        Timer().scheduleAtFixedRate(timerTask{

            thisPet.decrementHappiness()
            thisPet.decrementCleanliness()
            thisPet.decrementHunger()
            happinessProgress.progress = thisPet.getHappiness()
            hungerProgress.progress = thisPet.getHunger()
            cleanlinessProgress.progress = thisPet.getCleanliness()
            Log.e("Timer went off", thisPet.getHappiness().toString())
        }, 2000, 15000)

        if(thisPet.getType() == "Cat"){
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.happycat))
            //petSprite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.new_cat_btn))
            setCatWalking()
        }else if (thisPet.getType() == "Dog"){
            petSprite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.new_dog_btn))
        }
    }

    private fun setCatWalking() {

        var thisAnim = petSprite.drawable as AnimationDrawable
        thisAnim.start()
    }


}


