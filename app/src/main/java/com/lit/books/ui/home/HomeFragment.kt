package com.lit.books.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import com.google.gson.GsonBuilder
import com.lit.books.MainActivity
import com.lit.books.R
import com.lit.books.adapters.RecyclerAdapter
import com.lit.books.databinding.FragmentHomeBinding
import com.lit.books.models.Books
import com.lit.books.services.RestApiService
import kotlinx.android.synthetic.main.fragment_dashboard.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var recyclerAdapter: RecyclerAdapter //call the adapter
    lateinit var progressbar: ProgressBar
    lateinit var noservices: MaterialTextView
    lateinit var pull: MaterialTextView
    lateinit var ggggg: MaterialTextView
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //getFragmentManager()?.beginTransaction()?.detach(this)?.attach(this)?.commit();
        val prefs1 = activity?.getSharedPreferences("storage", AppCompatActivity.MODE_PRIVATE)
        val emailP = prefs1?.getString("email", "")
        val user = binding.user
        val logout = binding.logout
        logout.visibility = View.GONE
        if (emailP!!.isEmpty()){
            user.text = "Not Logged In"
            logout.visibility = View.GONE
        }
        else {
            user.text = "Current User:\n$emailP"
            logout.visibility = View.VISIBLE
        }

        logout.setOnClickListener {
            val prefs = activity?.getSharedPreferences("storage", AppCompatActivity.MODE_PRIVATE)
            val editor = prefs?.edit()
            editor?.clear()
            editor?.apply()

            val prefs2 = activity?.getSharedPreferences("store", AppCompatActivity.MODE_PRIVATE)
            val editor2 = prefs2?.edit()
            editor2?.clear()
            editor2?.apply()
            activity?.startActivity(Intent(activity, MainActivity::class.java))
            activity?.finishAffinity()
        }

        //val textView: TextView = binding.textHome
        progressbar = binding.progressbar
        recyclerView = binding.recycler
        noservices = binding.noservices
        pull = binding.pullrefresh
        ggggg = binding.ggggg
        swipeRefreshLayout = binding.container
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
        ggggg.typeface = typeface

        recyclerAdapter = RecyclerAdapter(requireActivity())
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.setHasFixedSize(true)

        val apiService = RestApiService()
        //pass the driver id below
        apiService.books(){
            if(it == null){
                //Toast.makeText(activity, "Connection Error, Please Check, Reload App", Toast.LENGTH_SHORT).show()
                noservices.visibility = View.VISIBLE
                noservices.text = "No Internet Connection. "
                progressbar.visibility = View.GONE
                pull.visibility = View.VISIBLE
                recyclerView.adapter = recyclerAdapter
            }
            else {
                pull.visibility = View.VISIBLE
                noservices.visibility = View.GONE
                if (it!!.userData == null) {
                    Toast.makeText(requireActivity(), it.userMsg, Toast.LENGTH_SHORT).show()
                    progressbar.visibility = View.GONE
                    noservices.visibility = View.VISIBLE
                } else {
                    noservices.visibility = View.GONE
                    val gson = GsonBuilder().create()
                    val list = gson.fromJson(
                        it.userData,
                        Array<Books>::class.java
                    ).toList()
                    //now pass the converted list to adapter
                    recyclerAdapter.setProductListItems(list)
                    progressbar.visibility = View.GONE
                    println("resppp " + it.userData)
                    recyclerView.adapter = recyclerAdapter
                }
            }
        }


        swipeRefreshLayout.setOnRefreshListener {
            progressbar.visibility = View.VISIBLE
            // on below line we are setting is refreshing to false.
            swipeRefreshLayout.isRefreshing = false
            // on below line we are shuffling our list using random
            apiService.books(){
                if(it == null){
                    Toast.makeText(requireContext(), "Connection Error, Please Check, Reload App1", Toast.LENGTH_SHORT).show()
                    noservices.visibility = View.VISIBLE
                    noservices.text = "No Connection. "
                    progressbar.visibility = View.GONE
                }

                else {
                    noservices.visibility = View.GONE
                    if (it!!.userData == null) {
                        Toast.makeText(requireActivity(), it.userMsg, Toast.LENGTH_SHORT).show()
                        progressbar.visibility = View.GONE
                        noservices.visibility = View.VISIBLE
                    } else {
                        noservices.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        val list = gson.fromJson(
                            it.userData,
                            Array<Books>::class.java
                        ).toList()
                        //now pass the converted list to adapter
                        recyclerAdapter.setProductListItems(list)
                        progressbar.visibility = View.GONE
                        println("resppp " + it.userData)
                        recyclerView.adapter = recyclerAdapter
                    }
                }
            }

            // on below line we are notifying adapter
            // that data has changed in recycler view.
            recyclerAdapter.notifyDataSetChanged()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}