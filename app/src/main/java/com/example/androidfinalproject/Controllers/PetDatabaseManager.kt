package com.example.androidfinalproject.Controllers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidfinalproject.Models.Pet

val TABLE_PETS= "Pets"
val OWNERNAME = "OwnerName"
val PETNAME = "PetName"
val PETTYPE = "PetType"
val HAPPINESS = "Happiness"
val HUNGER = "Hunger"
val CLEANLINESS = "Cleanliness"
val DEFAULTLEVELS = 100

class PetDatabaseManager(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "pets.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_PETS} (" +
                "${OWNERNAME} TEXT, " +
                "${PETNAME} TEXT, " +
                "${PETTYPE} TEXT, " +
                "${HAPPINESS} INTEGER DEFAULT ${DEFAULTLEVELS}, " +
                "${HUNGER} INTEGER DEFAULT ${DEFAULTLEVELS}, " +
                "${CLEANLINESS} INTEGER DEFAULT ${DEFAULTLEVELS}, " +
                "PRIMARY KEY(${OWNERNAME}, ${PETNAME}) );")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE_USER")
        onCreate(db)
    }

    fun insert(petToInsert: Pet){
        val db = this.writableDatabase
        val insertString = "INSERT INTO $TABLE_PETS VALUES ('${petToInsert.getOwnerName()}', '${petToInsert.getName()}', '${petToInsert.getType()}')"
        db.execSQL(insertString)
    }

    fun update(happinessLevel:Int, hungerLevel: Int, cleanlinessLevel:Int, ownerName:String, petName:String)
    {
        val db = this.writableDatabase
        val updateString= "UPDATE $TABLE_PETS " +
                "SET $HUNGER =  '$hungerLevel', $HAPPINESS = '$happinessLevel', $CLEANLINESS = '$cleanlinessLevel' " +
                "WHERE '$petName' = $PETNAME AND '$ownerName' = $OWNERNAME"
        db.execSQL(updateString)
    }

    fun hasPet(username:String): Boolean{
        val db = this.writableDatabase
        var loginQuery = "SELECT ${OWNERNAME} FROM $TABLE_PETS WHERE ${OWNERNAME}='${username}'"

        var cursor = db.rawQuery(loginQuery, null)
        var count = cursor.count
        if(count >= 1){
            return true;
        }
        return false
    }

}