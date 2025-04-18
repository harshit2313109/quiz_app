package com.example.quizaki_

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class navigationbaractivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_navigationbaractivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize BottomNavigationView
        bottomNavigation = findViewById(R.id.bottom_navigation)

        // Set default fragment
        replaceFragment(Home())

        // Handle navigation item clicks
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav1 -> replaceFragment(Home())
                R.id.nav2 -> replaceFragment(Quizes())
                R.id.nav3 -> replaceFragment(Cloud_Computing_Cell())
                R.id.nav4 -> replaceFragment(Proflie())
            }
            true
        }
    }

    // Function to replace fragments dynamically
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }
}