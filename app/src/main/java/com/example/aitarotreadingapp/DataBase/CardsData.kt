package com.example.aitarotreadingapp.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CardsData(
    @PrimaryKey(autoGenerate = false)
    val name_short: String,
    val name: String,
)