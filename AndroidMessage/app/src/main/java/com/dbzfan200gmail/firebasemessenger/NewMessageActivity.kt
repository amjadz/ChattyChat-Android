package com.dbzfan200gmail.firebasemessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"
        val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
        supportActionBar?.title = username

        fetchUsers()
    }
    companion object {
        val USER_KEY = "USER_KEY"
    }


    private fun fetchUsers() {
       val ref =  FirebaseDatabase.getInstance().getReference("/users")

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach{
                    val user = it.getValue(UserModel::class.java)
                    if(user != null){
                        adapter.add(UserItem(user))


                    }
                }

                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    val user = intent.putExtra(USER_KEY, userItem.user)

                    startActivity(intent)

                    finish()


                }

                recyclerview_newmessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {


            }
        })
    }
}

class UserItem(val user: UserModel): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_row_new_message

    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_textview.text = user.username
        Picasso.get().load(user.profileImage).into(viewHolder.itemView.user_image_circle)
    }
}

