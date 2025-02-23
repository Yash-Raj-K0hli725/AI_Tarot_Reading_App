package com.example.aitarotreadingapp.DataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CardsData(
    val desc: String,
    val meaning_rev: String,
    val meaning_up: String,
    val name: String,
    @PrimaryKey(autoGenerate = false)
    val name_short: String,
    val suit: String?,
    val type: String,
) : Parcelable