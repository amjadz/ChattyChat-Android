package com.dbzfan200gmail.firebasemessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latestest_messages.*
import kotlinx.android.synthetic.main.latest_message_row.view.*
import java.security.acl.Group

class LatestestMessages : AppCompatActivity() {
    val adapter = GroupAdapter<ViewHolder>()


    companion object {
        var curreentUser: UserModel? = null

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latestest_messages)

        recyclerview_latest_messages.adapter = adapter


        listenForLatestMessage()
        fetchCurrentUser()

        userLogin()
    }

    class LatestMesasgeRow(val chatMessage: ChatMessage): Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
//            viewHolder.itemView.textView_username_latest_message.text = chatMessage.
            viewHolder.itemView.textView_latest_message.text = chatMessage.text
        }

        override fun getLayout(): Int {
            return R.layout.latest_message_row

        }


    }

    val messageMap = HashMap<String, ChatMessage>()

    private fun refresh(){
        adapter.clear()
        messageMap.values.forEach {
            adapter.add((LatestMesasgeRow(it)))

        }


    }

    private fun listenForLatestMessage(){
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                messageMap[p0.key!!] = chatMessage
                refresh()


                adapter.add(LatestMesasgeRow(chatMessage))


            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                messageMap[p0.key!!] = chatMessage
                refresh()



            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }


        })


    }


    private fun  fetchCurrentUser(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                curreentUser = p0.getValue(UserModel::class.java)



            }


        })

    }

    private fun userLogin(){

        val uid = FirebaseAuth.getInstance().uid

        if(uid == null){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TASK).or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            userLogin()


        }


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_new_mesage -> {
            val intent = Intent(this, NewMessageActivity::class.java)
            startActivity(intent)


            }

            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }


        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)


    }

}
