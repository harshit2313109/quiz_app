package com.example.quizaki_

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.NonCancellable.start

class swipeToUnlock : AppCompatActivity() {
    private lateinit var swipeIcon: ImageView
    private lateinit var swipeText: TextView
    private lateinit var swipeButton: LinearLayout
    private var initialX = 0f
    private var maxSwipeDistance = 0f
    private var isUnlocked = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_swipe_to_unlock)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeToUnlockid)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        swipeIcon = findViewById(R.id.swipeIcon)
        swipeText = findViewById(R.id.swipeText)
        swipeButton = findViewById(R.id.swipeButton)

        // Set up swipe action
        swipeIcon.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = view.x
                    maxSwipeDistance = swipeButton.width - view.width.toFloat()
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX - view.width / 2
                    if (newX > initialX && newX <= maxSwipeDistance + initialX) {
                        view.x = newX
                        // Fade out text as the arrow moves
                        val progress = (newX - initialX) / maxSwipeDistance
                        swipeText.alpha = 1 - progress
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (view.x >= maxSwipeDistance) {
                        isUnlocked = true
                        launchRecaptchaActivity()
                    } else {
                        // Animate back to start if not swiped fully
                        ValueAnimator.ofFloat(view.x, initialX).apply {
                            duration = 200
                            addUpdateListener {
                                val animatedValue = it.animatedValue as Float
                                view.x = animatedValue
                                swipeText.alpha = 1 - (animatedValue - initialX) / maxSwipeDistance
                            }
                            start()
                        }
                    }
                }
            }
            true
        }
    }

    // Launch reCAPTCHA Activity after successful swipe
    private fun launchRecaptchaActivity() {
        val intent = Intent(this, rechapcha_screen::class.java)
        startActivity(intent)
        finish()
    }
}