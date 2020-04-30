package com.example.androidfinalproject.Controllers

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.androidfinalproject.Models.User
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.activity_register_user.*
import java.sql.Types.NULL

class RegisterUserActivity : AppCompatActivity() {
var enterCheck = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

    }

    inner class insertAsync: AsyncTask<User, Unit, Unit>() {
        override fun doInBackground(vararg p0: User) {
            val myDb= UserDatabaseManager(applicationContext)
            myDb.writableDatabase
            if(!myDb.checkForRepeatedEntrance(p0[0].username)){
                enterCheck = true;
            }else{
                myDb.insert(p0[0])
            }

            return
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            if(enterCheck){
                Toast.makeText(applicationContext, "Username Already Exists", Toast.LENGTH_LONG).show()
                passwordCheckTxt.text.clear()
                newUserNameTxt.text.clear()
                enterPasswordTxt.text.clear()
            }else{
                finish()
            }

        }
    }

    fun goBack(view: View) {
        finish()
    }

    fun createAccount(view: View) {
        if(passwordCheckTxt.text.toString() != enterPasswordTxt.text.toString()){
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show()
        }
        else{
            val newUser = User(newUserNameTxt.text.toString(), enterPasswordTxt.text.toString())
            var createNewUserAsync = insertAsync()
            createNewUserAsync.execute(newUser)
        }
    }
}
