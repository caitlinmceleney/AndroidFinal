package com.example.androidfinalproject.Controllers

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isNotEmpty
import com.example.androidfinalproject.Models.Pet
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.activity_pet_picker.*
import java.sql.Types.NULL

class PetPickerActivity : AppCompatActivity() {
var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_picker)
        username = intent.getStringExtra("Username")
    }

    inner class insertAsync: AsyncTask<Pet, Unit, Unit>() {
        override fun doInBackground(vararg p0: Pet) {
            val myDb= PetDatabaseManager(applicationContext)
            myDb.writableDatabase
            myDb.insert(p0[0])
            return
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            val myIntent = Intent(applicationContext, PetPage::class.java)
            myIntent.putExtra("Username", username)
            startActivity(myIntent)
            }
        }


    fun petSelected(view: View) {

        if((catBtn.isChecked|| dogBtn.isChecked) && petName.text.toString().isNotEmpty()){
            var petType = "";
            when(petBtn.checkedRadioButtonId){
                catBtn.id -> petType = "Cat"
                dogBtn.id -> petType = "Dog"
            }
            var enteredPetName = petName.text.toString()
            var thisPet = Pet(petType, enteredPetName, username, NULL, NULL, NULL)
            var enterPetAsync = insertAsync()
            enterPetAsync.execute(thisPet)

        }else{
            Toast.makeText(applicationContext, "Please Make Sure All Fields Are Filled Out!", Toast.LENGTH_LONG).show()
        }
    }
}
