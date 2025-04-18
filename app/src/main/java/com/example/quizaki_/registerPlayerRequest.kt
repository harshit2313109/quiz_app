package com.example.quizaki_

data class registerPlayerRequest(
    val playerName: String,
    val uid: String,
    val email: String,
    val recaptchaToken: String
)
