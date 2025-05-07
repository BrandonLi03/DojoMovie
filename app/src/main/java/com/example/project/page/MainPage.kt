package com.example.project.page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project.fragments.HistoryFragment
import com.example.project.fragments.HomeFragment
import com.example.project.fragments.LocationFragment
import com.example.project.fragments.ProfileFragment
import com.example.project.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPage : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    val userId = intent.getIntExtra("USER_ID", -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        bottomNav = findViewById(R.id.bottomNav)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.history -> {
                    intent.putExtra("USER_ID", userId)
                    loadFragment(HistoryFragment())
                    true
                }
                R.id.location -> {
                    loadFragment(LocationFragment())
                    true
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}