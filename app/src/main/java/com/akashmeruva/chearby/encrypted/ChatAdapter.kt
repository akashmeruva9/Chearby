package com.akashmeruva.chearby.encrypted

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.chearby.R

class ChatAdapter(val context : Context, val chatList  : ArrayList<User>) : RecyclerView.Adapter<ChatAdapter.Chat_Adapter_ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chat_Adapter_ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.message_item , parent , false)
        return ChatAdapter.Chat_Adapter_ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Chat_Adapter_ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class Chat_Adapter_ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {


    }
}