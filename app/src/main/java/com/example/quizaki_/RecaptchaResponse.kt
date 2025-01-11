package com.example.quizaki_

class RecaptchaResponse (
        val success: Boolean,          // true if verification succeeded
        val challenge_ts: String?,     // Timestamp of the challenge
        val hostname: String?,         // The hostname where the reCAPTCHA was solved
        val errorCodes: List<String>?  // List of error codes (if any)
    )