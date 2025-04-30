package com.example.quizaki_

data class QuizQuestion_dc(

val category: String,
val question: String,
val correctans : String,
val options: List<String> // instead of 4 separate fields

)


