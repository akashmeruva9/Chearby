package com.akashmeruva.chearby.encrypted

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.chearby.R
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EncryptedRecycler_chat_Adapter(val context : Context , val userlist  : ArrayList<User>) : RecyclerView.Adapter<EncryptedRecycler_chat_Adapter.EncryptedRecycler_chat_ViewHolder>() {

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncryptedRecycler_chat_ViewHolder {
  val view : View = LayoutInflater.from(context).inflate(R.layout.chat_item , parent , false)
  return EncryptedRecycler_chat_ViewHolder(view)
 }


    override fun onBindViewHolder(holder: EncryptedRecycler_chat_ViewHolder, position: Int) {

     val currentUser = userlist[position]
     holder.name_person.text = currentUser.name
     holder.recent_msg.text = currentUser.recent_msg
     Glide.with(holder.itemView.context).load(currentUser.image_link).into(holder.img)

     if(currentUser.count_msg == 0 )
     {
        holder.count_cv.visibility = View.INVISIBLE
     }else {
        holder.count.text = currentUser.count_msg.toString()
     }

     holder.ChatItemLayout.setOnClickListener {
         val intent = Intent(context , EncryptedChat::class.java)
         intent.putExtra("number",currentUser.phonenumber)
         context.startActivity(intent)
     }

 }

 override fun getItemCount(): Int {
  return userlist.size
 }

 class EncryptedRecycler_chat_ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
 {
      val name_person = itemView.findViewById<TextView>(R.id.chat_name)
      val img = itemView.findViewById<ImageView>(R.id.chat_item_img)
      val recent_msg = itemView.findViewById<TextView>(R.id.recent_msg)
      val count = itemView.findViewById<TextView>(R.id.count_of_msg)
      val count_cv = itemView.findViewById<CardView>(R.id.count_of_msg_cv)
      val ChatItemLayout = itemView.findViewById<ConstraintLayout>(R.id.chat_item_layout)

 }
}