package com.lit.books

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lit.books.databinding.ActivityBooksFragmentBinding

class BooksFragment : AppCompatActivity() {

    private lateinit var binding: ActivityBooksFragmentBinding
    companion object {
        lateinit var mainActivity: BooksFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = this
        binding = ActivityBooksFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val prefs1 = getSharedPreferences("storage", AppCompatActivity.MODE_PRIVATE)
//        val emailP = prefs1?.getString("email", "")
//        val user = binding.user
//        if (emailP!!.isEmpty()){
//            user.text = "Not Logged In"
//        }
//        else {
//            user.text = "Logged As:\n$emailP"
//        }
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_books_fragment)
        navController.run {
            popBackStack()
            navigate(R.id.navigation_home)
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}