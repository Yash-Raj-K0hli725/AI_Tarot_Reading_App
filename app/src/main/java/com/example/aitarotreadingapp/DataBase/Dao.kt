package com.example.aitarotreadingapp.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aitarotreadingapp.TarotApi.Response.Card

@Dao
interface Dao {

    @Insert
    suspend fun InsertAllTarotDetails(ApiResponse: List<CardsData>)

    @Query("SELECT * FROM CARDSDATA WHERE suit = :suit ")
    suspend fun Read(suit:String):List<CardsData>

    @Query("SELECT COUNT(*) FROM cardsdata")
    suspend fun Count():Int
}