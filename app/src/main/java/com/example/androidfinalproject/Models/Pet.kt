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
        hunger-=10
    }

    fun decrementCleanliness(){
        cleanliness -= 1
    }

    fun decrementHappiness() {
        happiness -= 10
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