package com.example.aitarotreadingapp.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
data class PreviousQueries(
    @PrimaryKey(autoGenerate = false)
    val question:String,
    val prevCards:List<String>,
    val AiResponse:String?)