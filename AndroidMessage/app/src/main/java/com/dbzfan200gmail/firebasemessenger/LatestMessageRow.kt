package com.dbzfan200gmail.firebasemessenger

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*


class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>() {
    var chatPartnerUser: UserModel? = null

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val chatPartnerId: String
        if(chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatPartnerId = chatMessage.toId

        } else {
            chatPartnerId = chatMessage.fromId

        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(UserModel::class.java)
                viewHolder.itemView.textView_username_latest_message.text = chatPartnerUser?.username

                Picasso.get().load(chatPartnerUser?.profileImage).into(viewHolder.itemView.imageView_latest_user_message)
            }


        })

        viewHolder.itemView.textView_latest_message.text = chatMessage.text
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row

    }


}