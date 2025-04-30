package com.example.quizaki_

import retrofit2.http.GET

interface home_rv_interface {
    @GET("photos")
    suspend fun getItems():ArrayList<banner_rv1_dc>

    @GET("photos")
    suspend fun getItems2():ArrayList<banner_rv2_dc>

    @GET("photos")
    suspend fun getItems3():ArrayList<banner_rv3_dc>

    @GET("questions")
    suspend fun getQuestions(): List<QuizQuestion_dc>
}