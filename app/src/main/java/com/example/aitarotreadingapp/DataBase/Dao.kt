package com.example.aitarotreadingapp.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aitarotreadingapp.TarotApi.Response.Card

@Dao
interface Dao {

    @Insert
    suspend fun InsertAllTarotDetails(ApiResponse: List<CardsData>)

    @Query("SELECT * FROM CARDSDATA WHERE suit = :nameShort ")
    suspend fun readWith(nameShort: String):List<CardsData>

    @Query("DELETE FROM CARDSDATA")
    suspend fun deleteAll()

    @Query("SELECT * FROM CARDSDATA")
    suspend fun readAll():List<CardsData>

    @Query("SELECT COUNT(*) FROM cardsdata")
    suspend fun Count():Int
}