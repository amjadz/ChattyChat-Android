package com.dbzfan200gmail.firebasemessenger

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login()
        goBack()
    }



    private fun login(){

        login_button.setOnClickListener {

            val emailLogin = email_login.text.toString()
            val passwordLogin = password_login.text.toString()

            if (emailLogin.isEmpty() || passwordLogin.isEmpty()) {
                Toast.makeText(this, "Please fill missing fields", Toast.LENGTH_SHORT).show()


            } else {
                firebaseSignIn(emailLogin, passwordLogin)

            }


        }


    }

    private fun firebaseSignIn(email: String, password: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                // Log in successfull, go to messenger

            }

            .addOnFailureListener {
                // Add Toast message


            }


    }

    private fun goBack(){
        back_to_registration.setOnClickListener {
            finish()
        }


    }


}