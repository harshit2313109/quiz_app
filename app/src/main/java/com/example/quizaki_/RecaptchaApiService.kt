package com.example.quizaki_
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RecaptchaApiService {
    @FormUrlEncoded
    @POST("recaptcha/api/siteverify")
    fun verifyRecaptcha(
        @Field("secret") secret: String,
        @Field("response") token: String
    ): Call<RecaptchaResponse>
}