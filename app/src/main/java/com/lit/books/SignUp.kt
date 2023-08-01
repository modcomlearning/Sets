package com.lit.books
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lit.books.models.UserInfoSignUp
import com.lit.books.services.RestApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progress
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUp.setOnClickListener{
            registerDummyUser(firstname.text.toString(),
            lastname.text.toString(), "N/A", phonenumber.text.toString(),
            emailadd.text.toString(), password1.text.toString(),
            confirm_password1.text.toString())
        }

        loginaccount.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun registerDummyUser(fname: String, lname: String, gender: String, phone: String, email: String, password: String,  confirm: String) {
        //progress.progressTintList(ColorStateList.valueOf(Color.RED))
        progress.visibility = View.VISIBLE
        val apiService = RestApiService()
        val userInfo = UserInfoSignUp(
            firstName = fname,
            lastName  = lname,
            userGender = gender,
            userEmail = email,
            userPhone = phone,
            userPassword = password,
            confirmuserPassword = confirm)

        apiService.registerUser(userInfo) {
            progress.visibility = View.GONE
            if(it == null){
                Toast.makeText(applicationContext, "Connection Error, Please Check.", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(applicationContext, "${it.userMsg}", Toast.LENGTH_SHORT).show()
                if (it.userMsg == "Registration successful"){
                    val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
                }

            }
        }
    }
}