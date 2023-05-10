package com.akashmeruva.chearby.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akashmeruva.chearby.R
import com.akashmeruva.chearby.databinding.FragmentEncryptedBinding
import com.akashmeruva.chearby.encrypted.AddUser
import com.akashmeruva.chearby.encrypted.EncryptedRecycler_chat_Adapter
import com.akashmeruva.chearby.encrypted.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class encrypted : Fragment(R.layout.fragment_encrypted) {

    private lateinit var binding: FragmentEncryptedBinding
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter : EncryptedRecycler_chat_Adapter
    private lateinit var  db : DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db =  FirebaseDatabase.getInstance().reference.child("Users")
        binding = FragmentEncryptedBinding.bind(view)
        userList = ArrayList()
        adapter  = EncryptedRecycler_chat_Adapter(requireContext() , userList)
        binding.encryptedChatRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.encryptedChatRecyclerview.adapter = adapter

        db.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()).child("friends")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for(postsnapshot in snapshot.children)
                    {
                        val currentUser = postsnapshot.getValue(User::class.java)
                        userList.add(currentUser!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val newList : ArrayList<User> = ArrayList()

                for(i in 0 until userList.size)
                {
                    if(userList[i].name.toString().lowercase().contains(s.toString().lowercase()))
                    {
                        newList.add(userList[i])
                    }
                }
                adapter  = EncryptedRecycler_chat_Adapter(requireContext() , newList)
                binding.encryptedChatRecyclerview.layoutManager = LinearLayoutManager(requireContext())
                binding.encryptedChatRecyclerview.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable) {}
        })


        binding.addChatBtn.setOnClickListener {
            val intent = Intent(context , AddUser::class.java)
            startActivity(intent)
        }
    }
}