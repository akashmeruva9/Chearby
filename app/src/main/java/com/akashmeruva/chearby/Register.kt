package com.akashmeruva.chearby

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.akashmeruva.chearby.databinding.FragmentOtpAuthBinding
import com.akashmeruva.chearby.databinding.FragmentRegisterBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Register : Fragment(R.layout.fragment_register) {

    private lateinit var binding : FragmentRegisterBinding
    val db = FirebaseDatabase.getInstance()
    val refrence = db.reference.child("Users")
    private lateinit var sp : SharedPreferences
    var strrefrence : StorageReference? = null
    var uri  = "empty"
    var urilink = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        sp = requireActivity().getSharedPreferences("userdetails" , Context.MODE_PRIVATE)
        val navController = findNavController()
        strrefrence = FirebaseStorage.getInstance().reference

        binding.registerBtn.setOnClickListener {

            if(checkx() == true)
            {
                val name = "${binding.fnameEt1.text} ${binding.lnameEt2.text}"
                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()).child("details")
                    .child("name").setValue(name)
                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()).child("details")
                    .child("email").setValue(binding.emailEt3.text.toString())
                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()).child("details")
                    .child("phonenumber").setValue((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())

                val editor = sp.edit()
                editor.putString("loginprocess" , "done")
                editor.commit()

                navController.navigate(R.id.action_register_to_home2)
                requireActivity().finish()
            }
        }

        binding.registerbackBtn.setOnClickListener{
            navController.navigate(R.id.action_register_to_login)
        }

        binding.registerTv5.setOnClickListener {
            val intent = Intent(context , TermsAndConditions::class.java)
            startActivity(intent)
        }

        binding.uploadProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .cropSquare()//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (data != null) {
                uri = data.dataString.toString()
                uploadimage(100)
            }


        } else if(resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadimage(i: Int) {

        val progresdialog = ProgressDialog(context)
        progresdialog.setTitle("Uploading Image")
        progresdialog.show()

        if (i == 100) {

            strrefrence = FirebaseStorage.getInstance().getReference("images/${FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()}Profile")
            strrefrence?.putFile(uri.toUri())?.addOnCompleteListener {
                Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
                urilink = true
                binding.profileImg.setImageURI(uri.toUri())
                strrefrence!!.downloadUrl.addOnSuccessListener {
                    refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()).child("details")
                        .child("image_link").setValue(it.toString())
                }
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }?.addOnFailureListener {
                Toast.makeText(context, "${it}", Toast.LENGTH_SHORT).show()
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }

        }

    }

    private fun checkx() : Boolean {
        var a = true;

        if(binding.fnameEt1.text.isNullOrEmpty())
        {
            a = false
            Toast.makeText(context , "Please enter your First name !" , Toast.LENGTH_SHORT).show()
            binding.fnameEt1.error = "Empty"
        }

        if(binding.lnameEt2.text.isNullOrEmpty())
        {
            a = false
            Toast.makeText(context , "Please enter your Last name !" , Toast.LENGTH_SHORT).show()
            binding.lnameEt2.error = "Empty"
        }

        if(binding.emailEt3.text.isNullOrEmpty())
        {
            a = false
            Toast.makeText(context , "Please enter your email !" , Toast.LENGTH_SHORT).show()
            binding.emailEt3.error = "Empty"
        }

        if(!binding.termsCheckBox.isChecked)
        {
            a = false
            Toast.makeText(context , "Please accept the terms and conditions !" , Toast.LENGTH_SHORT).show()
        }

        if(urilink == false )
        {
            Toast.makeText(context , "Please upload the profile photo !" , Toast.LENGTH_SHORT).show()
            a = false
        }
        return a
    }
}