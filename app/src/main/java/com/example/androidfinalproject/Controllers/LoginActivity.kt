package com.example.androidfinalproject.Controllers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfinalproject.Models.User
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {
var successfulLogin = true
    var username = ""
//    var prefs = getSharedPreferences("name", MODE_PRIVATE);
//    var isLoggedIn = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myDb= UserDatabaseManager(applicationContext)
        myDb.writableDatabase
//        if(isLoggedIn){
//            var username = prefs.getString("username",username)
//            val myIntent = Intent(applicationContext, PetPage::class.java)
//            myIntent.putExtra("Username", username)
//            startActivity(myIntent)
//            return;
//        }
//    updateView()
    }


    inner class loginAsync: AsyncTask<User, Unit, Unit>() {
        override fun doInBackground(vararg p0: User) {
            val myDb= UserDatabaseManager(applicationContext)
            myDb.writableDatabase
            username = p0[0].username

            while(username == ""){

            }
            if(!myDb.loginCheck(p0[0])){
                successfulLogin = false;
            }


            return
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            if(!successfulLogin){
                Toast.makeText(applicationContext, "Incorrect Username or Password", Toast.LENGTH_LONG).show()
                usernameTxt.text.clear()
                passwordTxt.text.clear()
            }else{
                val petDb = PetDatabaseManager(applicationContext)
                if(petDb.hasPet(username)){
//                    val editor =
//                        getSharedPreferences("name", Context.MODE_PRIVATE).edit()
//                    editor.putString("username", username)
//                    editor.putBoolean("isLoggedIn", true)
//                    editor.apply()
                    val myIntent = Intent(applicationContext, PetPage::class.java)
                    myIntent.putExtra("Username", username)
                    startActivity(myIntent)
                }else{
                    val editor =
                        getSharedPreferences("name", Context.MODE_PRIVATE).edit()
                    editor.putString("username", username)
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                    val myIntent = Intent(applicationContext, PetPickerActivity::class.java)
                    myIntent.putExtra("Username", username)
                    startActivity(myIntent)
                }

            }

        }
    }

    fun registerUser(view: View) {
        val myIntent= Intent(this, RegisterUserActivity::class.java)
        startActivity(myIntent)
    }
    fun loginUser(view: View) {
        var loginUser = User(usernameTxt.text.toString(), passwordTxt.text.toString())
        var myAsync = loginAsync()
        myAsync.execute(loginUser)
    }

//    fun updateView():Unit
//    {
//        checkusers.text=""
//        val myDb= UserDatabaseManager(applicationContext)
//        myDb.writableDatabase
//        val listToDispay=myDb.selectAll()
//        for(i in listToDispay)
//        {
//            checkusers.append("id: ${i.username}  name: ${i.password}\n")
//        }
//    }
}
