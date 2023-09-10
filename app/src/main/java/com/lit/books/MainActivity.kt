package com.lit.books
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lit.books.models.UserInfo
import com.lit.books.services.RestApiService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var mainActivity: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivity = this
        val prefs = getSharedPreferences("storage", MODE_PRIVATE)
        val emailP = prefs.getString("email", "")
        val passwordP = prefs.getString("password", "")
        if(emailP!!.isNotEmpty() && passwordP!!.isNotEmpty()){
            loginDummyUser(emailP, passwordP)
        }

        else {
            btnLogin.setOnClickListener {
                loginDummyUser(email.text.toString(), password.text.toString())
            }
        }

        btnLogin.setOnClickListener {
            loginDummyUser(email.text.toString(), password.text.toString())
        }

        freeaccount.setOnClickListener {
            //Toast.makeText(applicationContext, "Coming Soon!", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, SignUp::class.java)
            startActivity(intent)
            //finish()
        }

        forgot.setOnClickListener {
            //Toast.makeText(applicationContext, "Coming Soon!", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, Forgot::class.java)
            startActivity(intent)
            //finish()
        }
    }//end

    fun loginDummyUser(email: String, password: String) {
        //progress.progressTintList(ColorStateList.valueOf(Color.RED))
        progress.visibility = View.VISIBLE
        val apiService = RestApiService()
        val userInfo = UserInfo(
            userEmail = email,
            userPassword = password)

        apiService.loginUser(userInfo) {
            progress.visibility = View.GONE
            if(it == null){
                Toast.makeText(applicationContext, "Connection Error, Please Check.", Toast.LENGTH_SHORT).show()
            }
            else {
                println("This one ${it!!.userData}")
                println("This one ${it.userMsg}")
                println("This one ${it.userToken}")

                if (it.userToken == null) {
                    Toast.makeText(applicationContext, "${it.userMsg}", Toast.LENGTH_SHORT).show()
                } else {
                    //Shared prefs
                    val prefs = getSharedPreferences("storage", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString("Token", it.userToken)
                    editor.putString("userData", it.userData.toString())
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.apply()

                    //We Now navigate to Payment
                    val intent = Intent(applicationContext, BooksFragment::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    Toast.makeText(applicationContext, "${it.userMsg} ${", More Features Coming Soon!"}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}