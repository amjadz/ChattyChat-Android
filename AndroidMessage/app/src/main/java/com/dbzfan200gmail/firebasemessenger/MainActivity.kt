package com.dbzfan200gmail.firebasemessenger

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerButton()
        loginIntent()
        addPhoto()


    }

    private fun registerButton() {
        register_button.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            val username = name.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_LONG).show()
            } else {
                firebaseCreateUser(email, password)

            }
        }
    }

    private fun loginIntent(){
        textView.setOnClickListener {

            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)

        }

    }


    private fun firebaseCreateUser(email: String, password: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) { return@addOnCompleteListener }

            } .addOnFailureListener{
                Toast.makeText(this, "User was not able to be created", Toast.LENGTH_LONG).show()
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){



        }

    }

    private fun addPhoto(){
        add_image.setOnClickListener {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, 0)

        }
    }

}