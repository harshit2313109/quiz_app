package com.example.quizaki_

import android.media.RouteListingPreference
import retrofit2.http.GET

interface api_interface {
    @GET("photos")
    suspend fun getItems():ArrayList<banner_rv_dc>
}