package com.dbzfan200gmail.firebasemessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ChatLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)


        supportActionBar?.title = "Chat Log"

    }
}
