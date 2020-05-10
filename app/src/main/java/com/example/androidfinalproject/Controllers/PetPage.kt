package com.example.androidfinalproject.Controllers


import android.graphics.drawable.AnimationDrawable
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

        poopOne.visibility = View.INVISIBLE
        poopTwo.visibility = View.INVISIBLE
        poop3.visibility = View.INVISIBLE


        petNameTxt.text = thisPet.getName()
        happinessProgress.progress = thisPet.getHappiness()
        hungerProgress.progress = thisPet.getHunger()
        checkCleanliness()

        if(thisPet.getType() == "Cat"){
//            petSprite.setImageDrawable(resources.getDrawable(R.drawable.happycat))
            //petSprite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.new_cat_btn))
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
            Handler(Looper.getMainLooper()).post(Runnable{checkCleanliness()})
            myDb.update(thisPet.getHappiness(), thisPet.getHunger(), thisPet.getCleanliness(), thisPet.getOwnerName(), thisPet.getName())


        }, 2000, 1000)


    }

    private fun spriteWrapper(){
        //Log.e("Sprite Wrapper", thisPet.getType())
        if(thisPet.getType() == "Cat"){
            //petSprite.setImageDrawable(resources.getDrawable(R.drawable.happycat))
            //petSprite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.new_cat_btn))
            Handler(Looper.getMainLooper()).post(Runnable{setCatWalking()})
        }else if (thisPet.getType() == "Dog"){
            //petSprite.setImageDrawable(resources.getDrawable(R.drawable.happydog))
            Handler(Looper.getMainLooper()).post(Runnable{setDogWalking()})
        }
    }



    private fun setPoop(){
        //Log.e("hits set poop", thisPet.getCleanliness().toString())
        var cleanliness = thisPet.getCleanliness()
        if(cleanliness <= 15){
            thisPet.clean(44)
            //Log.e("Cleanliness", thisPet.getCleanliness().toString())
            checkCleanliness()
            spriteWrapper()
            return
        }
        if(cleanliness<45){
            thisPet.clean(69)
            //Log.e("Cleanliness", thisPet.getCleanliness().toString())
            checkCleanliness()
            spriteWrapper()
            return
        }

        if(cleanliness > 45 ){
            thisPet.clean(100)
            //Log.e("Cleanliness", thisPet.getCleanliness().toString())
            checkCleanliness()
            spriteWrapper()
            return
        }
//        if(cleanliness>=70){
//            thisPet.clean(100)
//            Log.e("Cleanliness", thisPet.getCleanliness().toString())
//            checkCleanliness()
//            return
//        }
    }

    private fun checkCleanliness(){
        var cleanliness = thisPet.getCleanliness()

        if(cleanliness>=70){
            //Log.e("Cleanliness in 100..70 ", cleanliness.toString())
            poopOne.visibility = View.INVISIBLE
            poopTwo.visibility = View.INVISIBLE
            poop3.visibility = View.INVISIBLE
        }
        if(cleanliness<=69 && cleanliness>=45){
            //Log.e("Cleanliness in 69..45 ", cleanliness.toString())
            poopOne.visibility = View.VISIBLE
            poopTwo.visibility = View.INVISIBLE
            poop3.visibility = View.INVISIBLE
        }
        if(cleanliness<45 && cleanliness >= 15){
            //Log.e("Cleanliness in 44..15", cleanliness.toString())
            poopOne.visibility = View.VISIBLE
            poopTwo.visibility = View.VISIBLE
            poop3.visibility = View.INVISIBLE
        }
        if(cleanliness < 15){
            //Log.e("Cleanliness else ", cleanliness.toString())
            poopOne.visibility = View.VISIBLE
            poopTwo.visibility = View.VISIBLE
            poop3.visibility = View.VISIBLE
        }
        spriteWrapper()

    }

    private fun setCatWalking() {
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
        if(thisPet.getWellBeingLevel() == "high"){
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.happydog))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()
        }
        if(thisPet.getWellBeingLevel() == "medium"){
            Log.e("medium", thisPet.getWellBeingLevel())
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.dogmedium))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()

        }
        if(thisPet.getWellBeingLevel()=="low"){
            Log.e("low", thisPet.getWellBeingLevel())
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.doglow))
            var thisAnim = petSprite.drawable as AnimationDrawable
            thisAnim.start()
        }
        var thisAnim = petSprite.drawable as AnimationDrawable
        thisAnim.start()
    }

    private fun petPet(){
        thisPet.pet()
        happinessProgress.progress = thisPet.getHappiness()
        spriteWrapper()
    }

    fun feedPet(view: View) {
        thisPet.feed()
        spriteWrapper()
        hungerProgress.progress = thisPet.getHunger()
    }


}


