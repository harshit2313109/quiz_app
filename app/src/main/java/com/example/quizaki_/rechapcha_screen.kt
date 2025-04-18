package com.example.quizaki_

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaClient
import com.google.android.recaptcha.RecaptchaException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response

class rechapcha_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rechapcha_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutforrecaptcha)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val email = findViewById<EditText>(R.id.emailhome)
        val username = findViewById<EditText>(R.id.usernamehome)
        val playeruid = findViewById<EditText>(R.id.player_uid)
        val Iamnotarobot = findViewById<Button>(R.id.Iamnotarobot)

        val toolbar: Toolbar = findViewById(R.id.toolbar_recaptcha_screen)
        setSupportActionBar(toolbar)
        // Enable the back button in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle back button click
        toolbar.setNavigationOnClickListener {
            onBackPressed()// or use NavUtils.navigateUpFromSameTask(this) if using fragments
        }





        fun saveUserData(email: String, username: String, playeruid: String) {
            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("EMAIL", email)
            editor.putString("USERNAME", username)
            editor.putString("PLAYERUID", playeruid)
            editor.apply()
        }

        Iamnotarobot.setOnClickListener {
            val emailText = email.text.toString()
            val usernameText = username.text.toString()
            val playeruid = playeruid.text.toString()
            if (emailText.isNotEmpty() && usernameText.isNotEmpty() && playeruid.isNotEmpty()) {
                saveUserData(emailText, usernameText, playeruid)
                //to call the function made in other class
                val app =   application as? CustomApplication
                app?.executeLoginAction()

            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}