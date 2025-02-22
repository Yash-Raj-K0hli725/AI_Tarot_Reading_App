package com.example.aitarotreadingapp.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreviousQueries(
    @PrimaryKey(autoGenerate = false)
    val question:String
)