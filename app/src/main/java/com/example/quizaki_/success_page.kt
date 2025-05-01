package com.example.quizaki_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class success_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_success_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val score = intent.getIntExtra("score",0)
        if (score != null) {
            val scoreView = findViewById<TextView>(R.id.score_here)
            scoreView.text = score.toString()

        }


        val backtohome = findViewById<Button>(R.id.backtohome_btn)
        backtohome.setOnClickListener {
            val intent = Intent(this, navigationbaractivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}