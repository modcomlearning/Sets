package com.lit.books
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.lit.books.models.PaymentInfo
import com.lit.books.services.RestApiService
import kotlinx.android.synthetic.main.activity_payment.*


class PaymentActivity : AppCompatActivity() {

    lateinit var yourCountDownTimer: CountDownTimer
    lateinit var mainHandler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        mainHandler = Handler(Looper.getMainLooper())

        val info = findViewById<MaterialTextView>(R.id.info)
        info.visibility = View.GONE


        val layout3 = findViewById<LinearLayout>(R.id.layout3)
        val layout2 = findViewById<LinearLayout>(R.id.layout2)
        layout3.visibility = View.GONE
        layout2.visibility = View.VISIBLE
        val prefs1 = getSharedPreferences("storage", MODE_PRIVATE)
        val emailP = prefs1.getString("email", "")

        val prefs: SharedPreferences = getSharedPreferences(
            "store",
            Context.MODE_PRIVATE
        )
        val book_id = prefs.getString("book_id", "")

        val userInfo = PaymentInfo(
            userAmount = "",
            userPhone = "",
            userEmail = emailP,
            userBookId = book_id)

        val apiService = RestApiService()
        apiService.paymentCheck(userInfo) {
            progress.visibility = View.GONE
            if(it == null){
                layout3.visibility = View.GONE
                layout2.visibility = View.GONE
                Toast.makeText(applicationContext, "Connection Error, Please Check1.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, BooksFragment::class.java))
                finish()
            }
            else {
                Toast.makeText(applicationContext, "${it.userMsg}", Toast.LENGTH_SHORT).show()
                if(it.userMsg == "Subscribed"){
                    layout3.visibility = View.GONE
                    layout2.visibility = View.VISIBLE
                    startActivity(Intent(applicationContext, SingleActivity::class.java))
                    finish()
                }
                else{
                    layout3.visibility = View.VISIBLE
                    layout2.visibility = View.GONE
                    payphoneNoLayout.visibility = View.GONE
                    //val prefs1 = getSharedPreferences("storage", MODE_PRIVATE)
                    //val emailP = prefs1.getString("email", "")
                    val passwordP = prefs1.getString("password", "")
                    if(emailP!!.isNotEmpty() && passwordP!!.isNotEmpty()){
                        payphoneNoLayout.visibility = View.VISIBLE
                    }
                    else {
                        payphoneNoLayout.visibility = View.GONE
                    }

                    btnMakePayment.setOnClickListener {
                        if(emailP.isNotEmpty() && passwordP!!.isNotEmpty()){
//                val intent = Intent(applicationContext, BookActivities::class.java)
//                startActivity(intent)
                            //Here
                            if(payphoneNo.text.toString().startsWith("254") && payphoneNo.text.toString().length == 12) {
                                paymentDummyUser(payphoneNo.text.toString(), "2")
                                Toast.makeText(
                                    applicationContext,
                                    "Please wait.. Initializing Payment",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else {
                                Toast.makeText(applicationContext, "Phone No Must be 12 Numbers, Format 2547XXXXXXXX.", Toast.LENGTH_LONG).show()
                            }
                        }
                        else {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }


                    //access the saved product_name from preferences and put in the TextView
                    val title = prefs.getString("product_name", "")
                    val services = prefs.getString("book_service", "")
                    if (title!!.isEmpty() || services!!.isEmpty() ){

                    }
                    else {
                        payingFor.text = "Buy: ${services} \nOf  BOOK TITLE: ${title}"
                    }

                }//end

            }
        }

     //here
    }//end


    fun paymentDummyUser(phone: String, amount: String) {
        info.visibility = View.GONE
        //progress.progressTintList(ColorStateList.valueOf(Color.RED))
        progress.visibility = View.VISIBLE
        btnMakePayment.text = "Complete Payment by\n Entering MPESA PIN on Pop Up Dialog"
        btnMakePayment.isEnabled = false


        val apiService = RestApiService()
        val prefs1 = getSharedPreferences("storage", MODE_PRIVATE)
        val emailP = prefs1.getString("email", "")

        val prefs: SharedPreferences = getSharedPreferences(
            "store",
            Context.MODE_PRIVATE
        )
        val book_id = prefs.getString("book_id", "")
        val userInfo = PaymentInfo(
            userAmount = amount,
            userPhone = phone,
            userEmail = emailP,
            userBookId = book_id)

        apiService.paymentUser(userInfo) {
            progress.visibility = View.GONE
            btnMakePayment.text = "....Payment in Progress\n May take upto 2 minutes"
            if(it == null){
                Toast.makeText(applicationContext, "Connection Error, Please Check.", Toast.LENGTH_LONG).show()
                btnMakePayment.text = "Proceed"
                btnMakePayment.isEnabled = true
            }
            else {
                progress.visibility = View.VISIBLE
                mainHandler.post(updateTextTask)
                yourCountDownTimer = object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        info.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        info.text =
                            "Payment seems not to have been Received, Try Again \n If you made Payment please Call 073XXXX for assistance."
                        btnMakePayment.text = "Proceed"
                        btnMakePayment.isEnabled = true
                        mainHandler.removeCallbacks(updateTextTask)
                    }
                }.start()
                Toast.makeText(applicationContext, "${it.userMsg}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    val updateTextTask = object : Runnable {
        override fun run() {
            checkPayWait()
            mainHandler.postDelayed(this, 3000)
        }
    }

    fun checkPayWait(){
        val prefs1 = getSharedPreferences("storage", MODE_PRIVATE)
        val emailP = prefs1.getString("email", "")

        val prefs: SharedPreferences = getSharedPreferences(
            "store",
            Context.MODE_PRIVATE
        )
        val book_id = prefs.getString("book_id", "")

        val userInfo = PaymentInfo(
            userAmount = "",
            userPhone = "",
            userEmail = emailP,
            userBookId = book_id)

        val apiService = RestApiService()
        apiService.paymentCheck(userInfo) {
            progress.visibility = View.GONE
            if(it == null){
                //Toast.makeText(applicationContext, "Connection Error, Please Check.Try Again", Toast.LENGTH_SHORT).show()
                //yourCountDownTimer.cancel()
                //mainHandler.removeCallbacks(updateTextTask)
            }

            else if(it.userMsg == "Subscribed"){
                yourCountDownTimer.cancel()
                Toast.makeText(applicationContext, "Payment Received", Toast.LENGTH_SHORT).show()
                mainHandler.removeCallbacks(updateTextTask)
                startActivity(Intent(applicationContext, SingleActivity::class.java))
                finish()
            }
            else {
                //Toast.makeText(applicationContext, "Please Wait .. Processing", Toast.LENGTH_SHORT).show()
            }
        }
    }
}