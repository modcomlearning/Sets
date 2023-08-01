package com.lit.books

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import com.google.gson.GsonBuilder
import com.lit.books.adapters.RecyclerAdapter
import com.lit.books.adapters.RecyclerAdapterActivity
import com.lit.books.models.ActivityModel
import com.lit.books.models.BookActivity
import com.lit.books.models.Books
import com.lit.books.models.UserInfo

import com.lit.books.services.RestApiService
import kotlinx.android.synthetic.main.activity_book_activities.*
import kotlinx.android.synthetic.main.activity_payment.*

class BookActivities : AppCompatActivity() {
    lateinit var recyclerAdapter: RecyclerAdapterActivity //call the adapter
   // lateinit var progressbar: ProgressBar
//    lateinit var noservices: MaterialTextView
//    lateinit var pull: MaterialTextView
//    lateinit var ggggg: MaterialTextView
//    lateinit var recyclerView: RecyclerView
   lateinit var swipeRefreshLayout: SwipeRefreshLayout
   companion object {
       lateinit var mainActivity: BookActivities
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_activities)
        mainActivity = this
        val typeface = ResourcesCompat.getFont(applicationContext, R.font.montserrat)
        ggggg.typeface = typeface
        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.container)
        recyclerAdapter = RecyclerAdapterActivity(applicationContext)
        recycler.layoutManager = GridLayoutManager(applicationContext, 2)
       recycler.setHasFixedSize(true)


        val prefs = getSharedPreferences("store", MODE_PRIVATE)
        val bookid = prefs.getString("book_id", "")
        val bookservice = prefs.getString("book_service", "")
       // Toast.makeText(applicationContext, "Book Id $bookid - $bookservice", Toast.LENGTH_SHORT).show()

        val apiService = RestApiService()
        val userInfo = ActivityModel(
            bookId = bookid,
            activityType = bookservice)
        //pass the driver id below
        apiService.activityBooks(userInfo){
            if(it == null){
                Toast.makeText(applicationContext, "Connection Error, Please Check, Reload App", Toast.LENGTH_SHORT).show()
                noservices.visibility = View.VISIBLE
                noservices.text = "No Internet Connection. "
                progressbar.visibility = View.GONE
                pullrefresh.visibility = View.VISIBLE
                recycler.adapter = recyclerAdapter
            }

            else {
                pullrefresh.visibility = View.VISIBLE
                noservices.visibility = View.GONE
                if (it!!.userData == null) {
                    Toast.makeText(applicationContext, it.userMsg, Toast.LENGTH_SHORT).show()
                    progressbar.visibility = View.GONE
                    noservices.visibility = View.VISIBLE
                    val prefs: SharedPreferences = getSharedPreferences("store",
                        Context.MODE_PRIVATE)
                    //access the saved product_name from preferences and put in the TextView
                    val title = prefs.getString("product_name", "")
                    val services = prefs.getString("book_service", "")
                    noservices.text = "No: ${services} \nFor  BOOK TITLE: ${title}"


                } else {
                    noservices.visibility = View.GONE
                    val gson = GsonBuilder().create()
                    val list = gson.fromJson(
                        it.userData,
                        Array<BookActivity>::class.java
                    ).toList()
                    //now pass the converted list to adapter
                    recyclerAdapter.setProductListItems(list)
                    progressbar.visibility = View.GONE
                    println("resppp " + it.userData)
                    recycler.adapter = recyclerAdapter
                }
            }
        }


        swipeRefreshLayout.setOnRefreshListener {
            progressbar.visibility = View.VISIBLE
            // on below line we are setting is refreshing to false.
            container.isRefreshing = false
            // on below line we are shuffling our list using random
            apiService.activityBooks(userInfo){
                if(it == null){
                    Toast.makeText(applicationContext, "Connection Error, Please Check, Reload App", Toast.LENGTH_SHORT).show()
                    noservices.visibility = View.VISIBLE
                    noservices.text = "No Connection. "
                    progressbar.visibility = View.GONE
                }

                else {
                    noservices.visibility = View.GONE
                    if (it!!.userData == null) {
                        Toast.makeText(applicationContext, it.userMsg, Toast.LENGTH_SHORT).show()
                        progressbar.visibility = View.GONE
                        noservices.visibility = View.VISIBLE
                        val prefs: SharedPreferences = getSharedPreferences("store",
                            Context.MODE_PRIVATE)
                        //access the saved product_name from preferences and put in the TextView
                        val title = prefs.getString("product_name", "")
                        val services = prefs.getString("book_service", "")
                        noservices.text = "No: ${services} \nFor  BOOK TITLE: ${title}"

                    } else {
                        noservices.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        val list = gson.fromJson(
                            it.userData,
                            Array<BookActivity>::class.java
                        ).toList()
                        //now pass the converted list to adapter
                        recyclerAdapter.setProductListItems(list)
                        progressbar.visibility = View.GONE
                        println("resppp " + it.userData)
                        recycler.adapter = recyclerAdapter
                    }
                }
            }
            // on below line we are notifying adapter
            // that data has changed in recycler view.
            recyclerAdapter.notifyDataSetChanged()
        }



    }
}