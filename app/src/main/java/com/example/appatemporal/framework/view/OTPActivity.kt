package com.example.appatemporal.framework.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appatemporal.databinding.ActivityOtpactivityBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.OTPViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var OTP: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String
    private val otpViewModel: OTPViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        binding.otpProgressBar.visibility = View.INVISIBLE
        binding.otpEditText1.requestFocus()
        addTextChangeListener()
        resendOTPvVisibility()

        binding.resendTextView.setOnClickListener {
            resenderVerificationCode()
            resendOTPvVisibility()
        }

        binding.verifyOTPBtn.setOnClickListener {
            //colect otp from all the edit text
            val typedOTP = binding.otpEditText1.text.toString() + binding.otpEditText2.text.toString() + binding.otpEditText3.text.toString() +
                    binding.otpEditText4.text.toString() + binding.otpEditText5.text.toString() + binding.otpEditText6.text.toString()
            Log.d("typedOTP", typedOTP)
            if (typedOTP.isNotEmpty()){
                if (typedOTP.length == 6){
                    val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    binding.otpProgressBar.visibility = View.VISIBLE
                    signInWithPhoneAuthCredential(credential)
                }else{
                    Toast.makeText( this, "Please enter coerrect OTP", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText( this, "Please enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun resendOTPvVisibility(){
        binding.otpEditText1.setText("")
        binding.otpEditText2.setText("")
        binding.otpEditText3.setText("")
        binding.otpEditText4.setText("")
        binding.otpEditText5.setText("")
        binding.otpEditText6.setText("")
        binding.resendTextView.isEnabled = false
        binding.resendTextView.setTextColor(Color.parseColor("#606060"))

        Handler(Looper.myLooper()!!).postDelayed(Runnable{
            binding.resendTextView.setTextColor(Color.parseColor("#E4EBF1"))
            binding.resendTextView.isEnabled = true
        },60000)
    }

    private fun resenderVerificationCode(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            //signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request\
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            OTP = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.otpProgressBar.visibility = View.VISIBLE
                    Toast.makeText(this,"Authenticate Succesfully", Toast.LENGTH_SHORT).show()
                    val uid: String = auth.currentUser?.uid.toString()
                    val repository = Repository(this)
                    otpViewModel.verifyUser(uid, repository)
                    otpViewModel.userExists.observe(this, Observer {
                        val existence = it as Boolean
                        if (existence) {
                            val intent = Intent(this, Main::class.java)
                            intent.putExtra("userUid", uid)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this, RegisterActivity::class.java)
                            intent.putExtra("userUid", uid)
                            startActivity(intent)
                        }
                    })


                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG","SignInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                         Log.d("TAG","Incorrect SMS")
                    }
                    // Update UI
                }
            }
    }

    private fun addTextChangeListener(){
        binding.otpEditText1.addTextChangedListener(EditTextWatcher(binding.otpEditText1))
        binding.otpEditText2.addTextChangedListener(EditTextWatcher(binding.otpEditText2))
        binding.otpEditText3.addTextChangedListener(EditTextWatcher(binding.otpEditText3))
        binding.otpEditText4.addTextChangedListener(EditTextWatcher(binding.otpEditText4))
        binding.otpEditText5.addTextChangedListener(EditTextWatcher(binding.otpEditText5))
        binding.otpEditText6.addTextChangedListener(EditTextWatcher(binding.otpEditText6))
    }


    inner class EditTextWatcher(private val view : View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()
            when(view.id){
                binding.otpEditText1.id -> if(text.length == 1 ) binding.otpEditText2.requestFocus() else if (text.isEmpty()) binding.otpEditText1.requestFocus()
                binding.otpEditText2.id -> if(text.length == 1 ) binding.otpEditText3.requestFocus() else if (text.isEmpty()) binding.otpEditText1.requestFocus()
                binding.otpEditText3.id -> if(text.length == 1 ) binding.otpEditText4.requestFocus() else if (text.isEmpty()) binding.otpEditText2.requestFocus()
                binding.otpEditText4.id -> if(text.length == 1 ) binding.otpEditText5.requestFocus() else if (text.isEmpty()) binding.otpEditText3.requestFocus()
                binding.otpEditText5.id -> if(text.length == 1 ) binding.otpEditText6.requestFocus() else if (text.isEmpty()) binding.otpEditText4.requestFocus()
                binding.otpEditText6.id -> if(text.isEmpty()) binding.otpEditText5.requestFocus()
            }
        }
    }
}



