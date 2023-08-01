package com.lit.books
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_single.*

class SingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        //access shared prefferences
        val prefs: SharedPreferences = getSharedPreferences("store",
            Context.MODE_PRIVATE)
        //access the saved product_name from prefferences and put in the TextView
        val title = prefs.getString("product_name", "")
        val text_title = findViewById(R.id.p_name) as TextView
        text_title.text = title
        val typeface = ResourcesCompat.getFont(applicationContext, R.font.montserrat)
        text_title.typeface = typeface


        //access the saved product_desc from prefferences and put in the TextView
        val desc = prefs.getString("product_desc", "")
        val text_desc = findViewById(R.id.p_desc) as TextView
        text_desc.text = desc
        text_desc.typeface = typeface

        //access the saved product_cost from prefferences and put in the TextView
        val cost = prefs.getString("product_cost", "")
        val text_cost= findViewById(R.id.p_publisher) as TextView
        text_cost.text = cost
        text_cost.typeface = typeface

        //access the saved image from prefferences and put in the ImageView Using Glide
        val image_url = prefs.getString("image_url", "")
        val image = findViewById<ImageView>(R.id.img_url)
        Glide.with(applicationContext).load("https://hudumazetu.com/piks/$image_url")
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.placeholder))
            .into(image)


        pay.setOnClickListener {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("book_service", "characters")
            editor.apply()

            val intent = Intent(applicationContext, BookActivities::class.java)
            startActivity(intent)
        }

        payss.setOnClickListener {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("book_service", "themes")
            editor.apply()
            val intent = Intent(applicationContext, BookActivities::class.java)
            startActivity(intent)
        }

        payss1.setOnClickListener {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("book_service", "styles")
            editor.apply()
            val intent = Intent(applicationContext, BookActivities::class.java)
            startActivity(intent)
        }

        payssss.setOnClickListener {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("book_service", "analysis")
            editor.apply()
            val intent = Intent(applicationContext, BookActivities::class.java)
            startActivity(intent)
        }
    }
}