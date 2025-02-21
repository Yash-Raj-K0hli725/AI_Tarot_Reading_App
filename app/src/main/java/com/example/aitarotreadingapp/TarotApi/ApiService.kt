package com.example.aitarotreadingapp.TarotApi

import com.example.aitarotreadingapp.TarotApi.Response.Details
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("cards")
    fun getCardsDetails(): Call<Details>
}