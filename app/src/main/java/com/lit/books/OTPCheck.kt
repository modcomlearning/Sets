package com.lit.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.lit.books.models.Forgot
import com.lit.books.models.ForgotChange
import com.lit.books.services.RestApiService
import kotlinx.android.synthetic.main.activity_main.*

class OTPCheck : AppCompatActivity() {
    lateinit var editOTP: TextInputEditText
    lateinit var editPin1: TextInputEditText
    lateinit var editPin2: TextInputEditText
    lateinit var btnChange: MaterialButton
    lateinit var progress: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpcheck)
        progress = findViewById(R.id.progress)
        editOTP =  findViewById(R.id.otp)
        editPin1 =  findViewById(R.id.pin1)
        editPin2 =  findViewById(R.id.pin2)
        btnChange =  findViewById(R.id.btnChange)
        btnChange.setOnClickListener {

            if (editOTP.text.toString().length!=5 ){
                Toast.makeText(applicationContext, "OTP Must be 5 Digits", Toast.LENGTH_SHORT).show()
            }
            else if (editPin1.text.toString() != editPin2.text.toString() ){
                Toast.makeText(applicationContext, "Pin Do not Match", Toast.LENGTH_SHORT).show()
            }
            else if (editPin1.text.toString().length !=4 ){
                Toast.makeText(applicationContext, "Pin Must be 4 Digits", Toast.LENGTH_SHORT).show()
            }

            else {
                //call
                val prefs = getSharedPreferences("storage", MODE_PRIVATE)
                val phone = prefs.getString("phone", "")
                if (phone != null) {
                    changePin(editOTP.text.toString(), editPin1.text.toString() , phone)
                }
            }
        }
    }

    //Change
    //Get OTP
    fun changePin(otp: String, pin1: String, phone: String) {
        progress.visibility = View.VISIBLE
        val apiService = RestApiService()
        val userInfo = ForgotChange(
            userPhone = phone,
            userOtp = otp,
            userPin = pin1)

        apiService.forgotChange(userInfo) {
            progress.visibility = View.GONE
            if(it == null){
                Toast.makeText(applicationContext, "Connection Error, Please Check.", Toast.LENGTH_SHORT).show()
            }
            else {
                println("This one ${it.userMsg}")
                if (it.userMsg == "Invalid") {
                    Toast.makeText(applicationContext, "Wrong OTP", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, com.lit.books.Forgot::class.java))
                    finish()
                } else {
                    //Shared prefs
                    Toast.makeText(applicationContext, "${it.userMsg}. Please Login with New Pin.", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }
}