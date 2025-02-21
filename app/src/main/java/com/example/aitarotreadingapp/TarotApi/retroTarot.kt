package com.example.aitarotreadingapp.TarotApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retroTarot {
    const val BaseUrl = "https://tarotapi.dev/api/v1/"

    fun getInstance():Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}