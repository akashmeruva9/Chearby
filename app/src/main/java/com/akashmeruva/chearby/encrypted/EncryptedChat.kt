package com.akashmeruva.chearby.encrypted

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akashmeruva.chearby.R
import com.akashmeruva.chearby.databinding.ActivityEncryptedChatBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class EncryptedChat : AppCompatActivity(R.layout.activity_encrypted_chat) {

    private lateinit var binding : ActivityEncryptedChatBinding
    private lateinit var  db : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypted_chat)

        db =  FirebaseDatabase.getInstance().reference.child("Users")
        binding = ActivityEncryptedChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number = intent.extras!!.getString("number")

        db.child(number!!).child("details").child("name").get().addOnSuccessListener {
            binding.senderName.text = it.value.toString()
        }

        db.child(number!!).child("details").child("image_link").get().addOnSuccessListener {
            Glide.with(applicationContext).load(it.value.toString()).into(binding.chatImg)
        }

        binding.sendChatBtn.setOnClickListener {
            val text = binding.chatEditText.text.toString()
            val c = Calendar.getInstance()
            val cyear = c.get(Calendar.YEAR)
            val cmonth = c.get(Calendar.MONTH)
            val cday = c.get(Calendar.DAY_OF_MONTH)
            val Date = "$cday-${cmonth+1}-$cyear"
            val Time: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

            db.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()).child("friends").child(number)
                .child("chat").push().setValue(Chat(text , Date , Time , "sent" , number))
        }

    }
}