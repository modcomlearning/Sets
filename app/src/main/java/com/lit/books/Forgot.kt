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
import com.lit.books.models.UserInfo
import com.lit.books.services.RestApiService
import kotlinx.android.synthetic.main.activity_main.*

class Forgot : AppCompatActivity() {
    lateinit var editPhone: TextInputEditText
    lateinit var btnGet: MaterialButton
    lateinit var progress: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        editPhone = findViewById(R.id.phone)
        btnGet = findViewById(R.id.btnGet)
        progress = findViewById(R.id.progress)
        btnGet.setOnClickListener {
           if (editPhone.text.toString().length !=10){
               Toast.makeText(applicationContext, "Invalid Phone", Toast.LENGTH_SHORT).show()
           }

            else {
                getOTP(editPhone.text.toString())
           }

        }//end Listener
    }

    //Get OTP
    fun getOTP(phone: String) {
        //progress.progressTintList(ColorStateList.valueOf(Color.RED))
        progress.visibility = View.VISIBLE
        val apiService = RestApiService()
        val userInfo = Forgot(
           userPhone = phone)

        apiService.forgotUser(userInfo) {
            progress.visibility = View.GONE
            if(it == null){
                Toast.makeText(applicationContext, "Connection Error, Please Check.", Toast.LENGTH_SHORT).show()
            }
            else {
                println("This one ${it.userMsg}")
                if (it.userMsg == "Sent") {
                    Toast.makeText(applicationContext, "${it.userMsg}", Toast.LENGTH_SHORT).show()
                    val prefs = getSharedPreferences("storage", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString("phone", editPhone.text.toString())
                    editor.apply()
                    startActivity(Intent(applicationContext, OTPCheck::class.java))
                } else {
                    //Shared prefs
                    Toast.makeText(applicationContext, "${it.userMsg} ${", Check your Phone No and Try Again"}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}