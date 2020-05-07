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
            happinessProgress.progress = thisPet.getHappiness()
            hungerProgress.progress = thisPet.getHunger()
            Handler(Looper.getMainLooper()).post(Runnable { checkCleanliness() })

        }, 2000, 1000)

        if(thisPet.getType() == "Cat"){
            petSprite.setImageDrawable(resources.getDrawable(R.drawable.happycat))
            //petSprite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.new_cat_btn))
            setCatWalking()
        }else if (thisPet.getType() == "Dog"){
            petSprite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.new_dog_btn))
        }
    }

    private fun setPoop(){
        Log.e("hits set poop", thisPet.getCleanliness().toString())
        var cleanliness = thisPet.getCleanliness()
        if(cleanliness <= 15){
            thisPet.clean(44)
            Log.e("Cleanliness", thisPet.getCleanliness().toString())
            checkCleanliness()
            return
        }
        if(cleanliness<45){
            thisPet.clean(69)
            Log.e("Cleanliness", thisPet.getCleanliness().toString())
            checkCleanliness()
            return
        }

        if(cleanliness > 45 ){
            thisPet.clean(100)
            Log.e("Cleanliness", thisPet.getCleanliness().toString())
            checkCleanliness()
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

    }

    private fun setCatWalking() {

        var thisAnim = petSprite.drawable as AnimationDrawable
        thisAnim.start()
    }

    private fun petPet(){
        thisPet.pet()
        happinessProgress.progress = thisPet.getHappiness()
    }


}


