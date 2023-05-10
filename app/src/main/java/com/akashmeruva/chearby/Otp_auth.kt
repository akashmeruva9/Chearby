package com.akashmeruva.chearby

import `in`.aabhasjindal.otptextview.OTPListener
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.akashmeruva.chearby.databinding.FragmentOtpAuthBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class Otp_auth : Fragment(R.layout.fragment_otp_auth) {

    private lateinit var binding: FragmentOtpAuthBinding
    private var mAuth: FirebaseAuth? = null
    private var verificationId: String? = null
    lateinit var progresdialog : Dialog
    val db = FirebaseDatabase.getInstance()
    val refrence = db.reference.child("Users")
    private lateinit var sp : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtpAuthBinding.bind(view)
        val navController = findNavController()

        mAuth = FirebaseAuth.getInstance()
        sp = requireActivity().getSharedPreferences("userdetails" , Context.MODE_PRIVATE)

        binding.loginregiserBtn.isClickable = false
        binding.loginregiserBtn.isClickable = false
        binding.loginregiserBtn.isClickable = false
        binding.loginregiserBtn.setBackgroundColor(Color.WHITE)


        binding.getotpBtn.setOnClickListener {

            binding.ccp.registerCarrierNumberEditText(binding.number123)
            val phoneNumber = binding.ccp.fullNumberWithPlus.toString().trim()

            if (binding.number123.text.isNullOrEmpty()) {
                Toast.makeText(context, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show()
            }else
            {
                progresdialog = Dialog(requireContext())
                progresdialog.setContentView(R.layout.dialog_loader)
                progresdialog.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
                progresdialog.setCancelable(false)
                progresdialog.show()
                Toast.makeText(context, "$phoneNumber", Toast.LENGTH_SHORT).show()
                sendVerificationCode(phoneNumber.toString())
            }
        }

        binding.clickhereBtn.setOnClickListener {

            binding.otpcl.visibility = View.GONE
            binding.getotpBtn.isActivated = true
            binding.getotpBtn.isClickable = true
            binding.getotpBtn.isEnabled = true
            binding.getotpBtn.setBackgroundColor(Color.DKGRAY)

        }

        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }
            override fun onOTPComplete(otp: String) {
                verifyCode(otp)
            }
        }

        binding.splashbackBtn.setOnClickListener{
            navController.navigate(R.id.action_otp_auth_to_login)
        }

    }

    private fun sendVerificationCode(number: String) {

        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {

        val navController = findNavController()

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val progressDialog123 = ProgressDialog(context)
                    progressDialog123.setTitle("Getting Data...")
                    progressDialog123.show()

                    refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()).child("details")
                        .child("email").get().addOnSuccessListener {

                            val value = it.value.toString()

                            if(value == null || value == "null")
                            {
                                if(progressDialog123.isShowing)
                                    progressDialog123.dismiss()

                                binding.loginregiserBtn.isClickable = true
                                binding.loginregiserBtn.isClickable = true
                                binding.loginregiserBtn.isClickable = true
                                binding.loginregiserBtn.setBackgroundColor(Color.DKGRAY)
                                binding.loginregiserBtn.text = "Register"


                                val editor = sp.edit()
                                editor.putString("loginprocess" , "shouldRegister")
                                editor.commit()

                                binding.loginregiserBtn.setOnClickListener {
                                    navController.navigate(R.id.action_otp_auth_to_register)
                                }

                            }else
                            {
                                if(progressDialog123.isShowing)
                                    progressDialog123.dismiss()

                                binding.loginregiserBtn.isClickable = true
                                binding.loginregiserBtn.isClickable = true
                                binding.loginregiserBtn.isClickable = true
                                binding.loginregiserBtn.setBackgroundColor(Color.DKGRAY)
                                binding.loginregiserBtn.text = "Login"

                                val editor = sp.edit()
                                editor.putString("loginprocess" , "done")
                                editor.commit()

                                binding.loginregiserBtn.setOnClickListener {
                                    navController.navigate(R.id.action_otp_auth_to_home2)
                                }
                            }

                        }.addOnFailureListener{

                            Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
                        }



                } else {

                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)

                if(progresdialog.isShowing)
                    progresdialog.dismiss()
                verificationId = s
                binding.otpcl.visibility = View.VISIBLE
                binding.getotpBtn.isActivated = false
                binding.getotpBtn.isClickable = false
                binding.getotpBtn.isEnabled = false
                binding.getotpBtn.setBackgroundColor(Color.WHITE)

                binding.clickhereBtn.isActivated = false
                binding.clickhereBtn.isEnabled = false
                binding.clickhereBtn.isClickable = false
                binding.clickhereBtn.setBackgroundColor(Color.WHITE)

                start_timer()

            }


            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                if(progresdialog.isShowing)
                    progresdialog.dismiss()

                val code = phoneAuthCredential.smsCode

                if (code != null) {
                    binding.otpView.setOTP(code)
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {

                if(progresdialog.isShowing)
                    progresdialog.dismiss()

                Log.d( "Error" , e.message!!)
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }

        }

    private fun start_timer() {
        object : CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.countdown.text = "after ${millisUntilFinished / 1000} secs"
            }

            override fun onFinish() {
                binding.clickhereBtn.isActivated = true
                binding.clickhereBtn.isEnabled = true
                binding.clickhereBtn.isClickable = true
                binding.clickhereBtn.setBackgroundColor(Color.DKGRAY)
            }
        }.start()
    }


    private fun verifyCode(code: String) {

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }
}