package com.example.quizaki_

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaClient
import com.google.android.recaptcha.RecaptchaException


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.HttpException

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CustomApplication : Application() {

     lateinit var recaptchaClient: RecaptchaClient
    // we recommend initializing in a ViewModel
    private val recaptchaScope = CoroutineScope(Dispatchers.IO)


    override fun onCreate() {
        super.onCreate()


        initializeRecaptchaClient()

    }

     fun initializeRecaptchaClient() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = Recaptcha.fetchClient(this@CustomApplication ,"6LfDeQErAAAAALqVPx58R9l1AvAYilY9eYzjj9EL")
                recaptchaClient = client

                Log.d("Recaptcha", "Client initialized successfully")
                }
             catch (e: RecaptchaException) {
                Log.e("Recaptcha", "Exception: ${e.message}")
            }
        }
    }

     fun executeLoginAction() {
         if (!::recaptchaClient.isInitialized) {
             Log.e("Recaptcha", "recaptchaClient is not initialized yet!")
             return
         }

         Log.d("checking token things", "Client")
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = recaptchaClient.execute(RecaptchaAction.LOGIN)

                Log.d("DEBUG", "Recaptcha execute() completed")

                if (result == null) {
                    Log.e(
                        "DEBUG",
                        "Recaptcha execution returned null!"
                    )  // 🚨 Log if result is null
                } else {
                    result.onSuccess { token ->
                        Log.d("Recaptcha", "Token received: $token")
                        sendTokenToServer(token) // Send the token to your backend
                    }.onFailure { e ->
                        Log.e("Recaptcha", "Failed to get token: ${e.message}")
                    }
                }
            }catch (e: Exception) {
                Log.e("Recaptcha", "Exception: ${e.message}")
            }
        }
    }

    private fun sendTokenToServer(token: String) {

        val sharedPref = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = sharedPref.getString("USERNAME", "No Username") ?: "No Username"
        val email = sharedPref.getString("EMAIL", "No Email") ?: "No Email"
        val playeruid = sharedPref.getString("PLAYERUID", "No Player UID") ?: "No Player UID"





        val retrofit = Retrofit.Builder()
            .baseUrl("https://ccc-quiz.onrender.com/") // Replace with your actual server URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(recaptcha_interface::class.java)

        val request = registerPlayerRequest(
            playerName = username.toString(),
            uid= playeruid.toString(),
            email = email.toString(),
            recaptchaToken = token.toString()

        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("Recaptcha", "Sending Token: $token")
                Log.d("Recaptcha", "With details: $username | $email | $playeruid")

                val result = apiService.registerPlayer(request)

                // If message is available, assume success (based on your backend structure)
                if (result.message != null) {
                    Log.d("Recaptcha", "Verification successful: ${result.message}")
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(applicationContext, "OTP sent to your email", Toast.LENGTH_SHORT).show()
                    }
                    navigateToOtpScreen()
                } else {
                    Log.e("Recaptcha", "Verification failed — response message is null")
                }

            } catch (e: HttpException) {
                val code = e.code()
                val message = e.message()
                val errorBody = e.response()?.errorBody()?.string()

                Log.e("Recaptcha", "HTTP error: $code - $message")
                Log.e("Recaptcha", "Error body: $errorBody")

            } catch (e: Exception) {
                Log.e("Recaptcha", "Unexpected error: ${e.message}", e)
            }
        }
    }

    private fun navigateToOtpScreen() {


        val sharedPref = applicationContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("EMAIL", "No Email")
        val username = sharedPref.getString("USERNAME", "No Username")


        Handler(Looper.getMainLooper()).post {
            val intent = Intent(this, otp_screen::class.java)
            intent.putExtra("EMAIL", email)
            intent.putExtra("USERNAME", username)

            startActivity(intent)
        }
    }
}









