
package com.Farmer.fra

import android.content.Intent
import android.media.session.MediaSession
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {
    private lateinit var sendotpbtn : Button
    private lateinit var phonenumber : EditText
    private lateinit var auth  : FirebaseAuth
    private lateinit var number  : String
    private lateinit var mprogresBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        sendotpbtn.setOnClickListener{
            number = phonenumber.text.trim().toString()
            if(number.isNotEmpty()){
                if(number.length == 10)
                {
                    number = "+91$number"
                    mprogresBar.visibility = View.VISIBLE
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)

                }else{
                    Toast.makeText(this,"Please Enter 10 Digit number",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Please Enter Number",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun init()
    {
        mprogresBar =  findViewById(R.id.phoneProgressBar)
        mprogresBar.visibility = View.INVISIBLE
        sendotpbtn = findViewById(R.id.sendOTPBtn)
        phonenumber = findViewById(R.id.phoneEditTextNumber)
        auth = FirebaseAuth.getInstance()
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    Toast.makeText(this,"Authenticate Successfully", Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()} ")

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    private fun sendToMain(){
        startActivity(Intent(this,MainPage::class.java))
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.


            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.


            // Save verification ID and resending token so we can use them later
            val intent = Intent(this@Login,otp::class.java)
            intent.putExtra("OTP",verificationId)
            intent.putExtra("resendToken",token)
            intent.putExtra("phoneNumber",number)
            startActivity(intent)
            mprogresBar.visibility = View.INVISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null)
        {
            startActivity(Intent(this,MainPage::class.java))
        }
    }
}