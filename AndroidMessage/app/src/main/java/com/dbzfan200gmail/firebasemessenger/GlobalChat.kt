package com.dbzfan200gmail.firebasemessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.activity_global_chat.*
import kotlinx.android.synthetic.main.chat_from_global_row.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_global_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class GlobalChat : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()
    var toUser: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_chat)


        toUser = intent.getParcelableExtra<UserModel>(NewMessageActivity.USER_KEY)

        global_chat.adapter = adapter


        listenForMessages()


        button_send_global_chat_log.setOnClickListener {
            preformSendMessage()


        }

    }

    private fun preformSendMessage(){
        val text = editText_global_chat_log.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<UserModel>(NewMessageActivity.USER_KEY)
        val toId = "dg"

        if(fromId == null) return

        val ref = FirebaseDatabase.getInstance().getReference("/global-chat/").push()

        val chatMessage = ChatMessage(ref.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)

        ref.setValue(chatMessage).addOnSuccessListener {
            Log.v("Message", "Message Sent")
            editText_global_chat_log.text.clear()
            global_chat.scrollToPosition(adapter.itemCount - 1)
        }

    }

    private fun listenForMessages(){
        val ref = FirebaseDatabase.getInstance().getReference("/global-chat/")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if(chatMessage != null) {
                    if(chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LatestestMessages.curreentUser

                        adapter.add(ChatFromGlobalItem(chatMessage.text, currentUser!!))

                    } else {
                        adapter.add(ChatToGlobalItem(chatMessage.text, toUser!!))

                    }

                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }


        })
    }

}

class ChatFromGlobalItem(val text: String, val user: UserModel): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_global_row

    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_from_global_row.text = text
        val uri = user.profileImage
        val toImage = viewHolder.itemView.imageView_chat_from_global_row
        Picasso.get().load(uri).into(toImage)

    }

}

class ChatToGlobalItem(val text: String, val user: UserModel): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_global_row

    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_to_global_item.text = text
        val uri = user.profileImage
        val toImage = viewHolder.itemView.imageView_chat_global_to_row
        Picasso.get().load(uri).into(toImage)

    }

}
