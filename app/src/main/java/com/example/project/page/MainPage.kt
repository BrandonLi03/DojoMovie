package com.example.project.page

import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val userId = intent.getIntExtra("USER_ID", -1)
        Log.d("MainPage", "Received USER_ID: $userId")

        bottomNav = findViewById(R.id.bottomNav)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val homeFragment = HomeFragment()
                    val bundle = Bundle()
                    bundle.putInt("USER_ID", userId)
                    homeFragment.arguments = bundle
                    loadFragment(homeFragment)
                    true
                }
                R.id.history -> {
                    val historyFragment = HistoryFragment()
                    val bundle = Bundle()
                    bundle.putInt("USER_ID", userId)
                    historyFragment.arguments = bundle
                    loadFragment(historyFragment)
                    true
                }
                R.id.location -> {
                    val locationFragment = LocationFragment()
                    val bundle = Bundle()
                    bundle.putInt("USER_ID", userId)
                    locationFragment.arguments = bundle
                    loadFragment(locationFragment)
                    true
                }
                R.id.profile -> {
                    val profileFragment = ProfileFragment()
                    val bundle = Bundle()
                    bundle.putInt("USER_ID", userId)
                    profileFragment.arguments = bundle
                    loadFragment(profileFragment)
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