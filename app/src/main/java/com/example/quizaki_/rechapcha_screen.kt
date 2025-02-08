package com.example.quizaki_

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.safetynet.SafetyNet
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

        val toolbar: Toolbar = findViewById(R.id.toolbar_recaptcha_screen)
        setSupportActionBar(toolbar)
        // Enable the back button in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle back button click
        toolbar.setNavigationOnClickListener {
            onBackPressed()// or use NavUtils.navigateUpFromSameTask(this) if using fragments
        }


        val safetyNetClient = SafetyNet.getClient(this)
        val recaptchaButton = findViewById<Button>(R.id.lets_go)

        // Call the verification API with Retrofit
       fun verifyTokenWithRetrofit(token: String) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.google.com/recaptcha/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(RecaptchaApiService::class.java)

            // Making the call asynchronous with enqueue
            apiService.verifyRecaptcha("6LfO7LIqAAAAAJj8LMRhki_W0gQQ7WLm8iPDKoQs", token)
                .enqueue(object : Callback<RecaptchaResponse> {
                    override fun onResponse(call: Call<RecaptchaResponse>, response: Response<RecaptchaResponse>) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(this@rechapcha_screen, "reCAPTCHA Verified Successfully!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@rechapcha_screen, "Verification Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<RecaptchaResponse>, t: Throwable) {
                        Toast.makeText(this@rechapcha_screen, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        recaptchaButton.setOnClickListener {
            safetyNetClient.verifyWithRecaptcha("AIzaSyABQ2otZSi4n4NQVSm9Uqj5dhsIwFcPqQA")
                .addOnSuccessListener { response ->
                    val token = response.tokenResult
                    if (!token.isNullOrEmpty()) {
                        Toast.makeText(this, "reCAPTCHA successful!", Toast.LENGTH_SHORT).show()
                        verifyTokenWithRetrofit(token) // Send to server for verification
                    }
                }
                .addOnFailureListener { e ->
                    Log.i("problem","finded")
                    Toast.makeText(this, "reCAPTCHA failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
