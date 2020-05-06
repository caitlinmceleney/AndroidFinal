package com.example.androidfinalproject.Models

class Pet (petType: String, thisPetName: String, thisOwnerName: String, thisHappiness:Int, thisHunger:Int, thisCleanliness:Int){
    private var happiness = thisHappiness
    private var hunger = thisHunger
    private var cleanliness = thisCleanliness
    private var totalWellBeing = happiness+hunger+cleanliness
    private val type = petType
    private val petName = thisPetName
    private val ownerName = thisOwnerName

    fun getType():String{
        return type
    }

    fun getName():String{
        return petName
    }
    fun getHappiness():Int{
        return happiness
    }
    fun getHunger():Int{
        return hunger
    }
    fun getCleanliness():Int{
        return cleanliness
    }

    fun getOwnerName():String{
        return ownerName
    }

    fun feed(){
        hunger = 100
    }

    fun clean(){
        cleanliness = 100
    }

    fun pet(){
        happiness = 100
    }


    fun decrementHunger(){
        hunger-=(0..10).random()
        if(hunger < 0){
            hunger = 0
        }
    }

    fun decrementCleanliness(){
        cleanliness -= (0..10).random()
        if(cleanliness < 0){
            cleanliness = 0
        }
    }

    fun decrementHappiness() {
        happiness -= (0..10).random()
        if(happiness < 0){
            happiness = 0
        }
    }

    fun passTime(){
        decrementHunger()
        decrementCleanliness()
        decrementHappiness()
        calculateWellBeing()
    }

    fun calculateWellBeing(){
        totalWellBeing = happiness+cleanliness+happiness
    }

    fun getWellBeingLevel():String{
        if(totalWellBeing<100){
            return "low"
        }else if(totalWellBeing in 100..199){
            return "medium"
        }else{
            return "high"
        }
    }



}