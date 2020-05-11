package com.example.androidfinalproject.Controllers

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidfinalproject.Models.User
import java.sql.Types.NULL

val TABLE_USER= "User"
val USERNAME = "Username"
val PASSWORD = "Password"

class UserDatabaseManager(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "users.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_USER} (" +
                "${USERNAME} TEXT PRIMARY KEY, " +
                "${PASSWORD} TEXT)")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists $TABLE_USER")
        onCreate(p0)
    }

    fun insert(userToInsert: User){
        val db = this.writableDatabase

        val insertString = "INSERT INTO $TABLE_USER VALUES ('${userToInsert.username}', '${userToInsert.password}')"
        db.execSQL(insertString)
    }

    fun checkForRepeatedEntrance(checkUsername: String) : Boolean{
        val db = this.writableDatabase
        var checkQuery = "SELECT * FROM $TABLE_USER WHERE ${USERNAME}='${checkUsername}'"
        var cursor = db.rawQuery(checkQuery, null)
        var count = cursor.count
        if(count > 0){
            return false
        }
        return true
    }

    fun loginCheck(loginUser : User) : Boolean{
        val db = this.writableDatabase
        var loginQuery = "SELECT ${USERNAME} FROM $TABLE_USER WHERE ${USERNAME}='${loginUser.username}' AND ${PASSWORD} = '${loginUser.password}'"
        var cursor = db.rawQuery(loginQuery, null)
        var count = cursor.count
        if(count == 1){
            return true;
        }
            return false
    }

    fun selectAll():ArrayList<User>
    {
        val sqlQuery = "select * from $TABLE_USER"

        val db = this.writableDatabase
        var toReturn= ArrayList<User>()
        var cursor=db.rawQuery(sqlQuery, null);

        while(cursor.moveToNext())
        {
            toReturn.add(User(cursor.getString(0), cursor.getString(1)))

        }
        return toReturn
    }

}