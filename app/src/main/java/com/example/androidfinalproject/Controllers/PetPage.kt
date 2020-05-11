package com.example.androidfinalproject.Controllers


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidfinalproject.Models.Pet
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.activity_pet_page.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.timerTask


class PetPage : AppCompatActivity() {
    var username = ""
    var thisPet = Pet("", "NULL", "NULL", 0 , 0,0)
    var thisPetWellBeing = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_page)
        username = intent.getStringExtra("Username")
        val myDb= PetDatabaseManager(applicationContext)
        myDb.writableDatabase
        myDb.getPet(username)
        thisPet = myDb.getPet(username)

        poopOne.visibility = View.INVISIBLE
        poopTwo.visibility = View.INVISIBLE
        poop3.visibility = View.INVISIBLE
        heart.visibility = View.INVISIBLE
        heart.scaleY = 0.0f
        heart.scaleX = 0.0f

        petNameTxt.text = thisPet.getName()
        happinessProgress.progress = thisPet.getHappiness()
        hungerProgress.progress = thisPet.getHunger()
        checkCleanliness()
        goOutsideTxt.text = "Go Outside!"
        if(thisPet.getType() == "Cat"){
            setCatWalking()
        }else if (thisPet.getType() == "Dog"){

            setDogWalking()
        }
        petSprite.setOnClickListener{
            petPet()
        }
        poopOne.setOnClickListener{
            setPoop()
        }
        poopTwo.setOnClickListener{
            setPoop()
        }
        poop3.setOnClickListener{
            setPoop()
        }



        Timer().scheduleAtFixedRate(timerTask{
            thisPet.decrementHappiness()
            thisPet.decrementCleanliness()
            thisPet.decrementHunger()
            thisPet.calculateWellBeing()
            happinessProgress.progress = thisPet.getHappiness()
            hungerProgress.progress = thisPet.getHunger()
            spriteWrapper()
            myDb.update(thisPet.getHappiness(), thisPet.getHunger(), thisPet.getCleanliness(), thisPet.getOwnerName(), thisPet.getName())
            //Handler(Looper.getMainLooper()).post(Runnable{heartDisappear(heart)})

        }, 2000, 5000)

        Timer().scheduleAtFixedRate(timerTask{
            spriteWrapper()
            Handler(Looper.getMainLooper()).post(Runnable{checkCleanliness()})
        }, 2000, 1000)


    }

    private fun spriteWrapper(){
        if (thisPet.getType() == "Cat") {
            Handler(Looper.getMainLooper()).post(Runnable{setCatWalking()})
        } else if (thisPet.getType() == "Dog") {
            Handler(Looper.getMainLooper()).post(Runnable { setDogWalking() })

        }
    }



    private fun setPoop(){
        var cleanliness = thisPet.getCleanliness()
        if(cleanliness <= 15){
            thisPet.clean(44)
            checkCleanliness()
            spriteWrapper()
            return
        }
        if(cleanliness<45){
            thisPet.clean(69)
            checkCleanliness()
            spriteWrapper()
            return
        }

        if(cleanliness > 45 ){
            thisPet.clean(100)
            checkCleanliness()
            spriteWrapper()
            return
        }
    }

    private fun checkCleanliness(){
        var cleanliness = thisPet.getCleanliness()

        if(cleanliness>=70){
            poopOne.visibility = View.INVISIBLE
            poopTwo.visibility = View.INVISIBLE
            poop3.visibility = View.INVISIBLE
        }
        if(cleanliness<=69 && cleanliness>=45){
            poopOne.visibility = View.VISIBLE
            poopTwo.visibility = View.INVISIBLE
            poop3.visibility = View.INVISIBLE
        }
        if(cleanliness<45 && cleanliness >= 15){
            poopOne.visibility = View.VISIBLE
            poopTwo.visibility = View.VISIBLE
            poop3.visibility = View.INVISIBLE
        }
        if(cleanliness < 15){
            poopOne.visibility = View.VISIBLE
            poopTwo.visibility = View.VISIBLE
            poop3.visibility = View.VISIBLE
        }
        spriteWrapper()

    }

    private fun setCatWalking() {
        if (thisPet.getWellBeingLevel() == thisPetWellBeing){
            return
        }
        thisPetWellBeing = thisPet.getWellBeingLevel()
        if(thisPet.getWellBeingLevel() == "high"){
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.happycat))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()
        }
        if(thisPet.getWellBeingLevel() == "medium"){
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.catmedium))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()

        }
        if(thisPet.getWellBeingLevel()=="low"){
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.catlow))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()
        }

    }
    private fun setDogWalking() {
        if (thisPet.getWellBeingLevel() == thisPetWellBeing){
            return
        }
        thisPetWellBeing = thisPet.getWellBeingLevel()
        if(thisPet.getWellBeingLevel() == "high"){
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.happydog))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()
        }
        if(thisPet.getWellBeingLevel() == "medium"){

            petSprite.setImageDrawable(resources.getDrawable(R.drawable.dogmedium))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()

        }
        if(thisPet.getWellBeingLevel()=="low"){

            petSprite.setImageDrawable(resources.getDrawable(R.drawable.doglow))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()
        }
        var thisAnim = petSprite.drawable as AnimationDrawable
        thisAnim.start()
    }

    fun heartAppear(view:View){
        heart.visibility = View.VISIBLE
        var animation = view.animate()
        animation.scaleX(1.0f)
        animation.scaleY(1.0f)
        animation.duration = 400
        animation.start()
        Handler(Looper.myLooper()).postDelayed(Runnable{heartDisappear(heart)}, 500)


    }
    fun heartDisappear(view:View){
        var animation = view.animate()
        animation.scaleY(0.0f)
        animation.scaleX(0.0f)
        animation.duration = 400
        animation.start()
    }

    private fun petPet(){
        thisPet.pet()
        happinessProgress.progress = thisPet.getHappiness()
        Handler(Looper.getMainLooper()).post(Runnable{heartAppear(heart)})
        spriteWrapper()
    }

    fun feedPet(view: View) {
        thisPet.feed()
        spriteWrapper()
        hungerProgress.progress = thisPet.getHunger()
    }

    fun changeBackground(view: View) {
        if(goOutsideTxt.text == "Go Outside!"){
            backgroundView.setImageDrawable(resources.getDrawable(R.drawable.outside_background))
            goOutsideTxt.text = "Go Back Inside!"
            return
        }
        if(goOutsideTxt.text.toString() == "Go Back Inside!"){
            goOutsideTxt.text = "Go Outside!"
            backgroundView.setImageDrawable(resources.getDrawable(R.drawable.background))
            return
        }
    }


}


