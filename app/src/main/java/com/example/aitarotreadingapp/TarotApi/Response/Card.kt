package com.example.aitarotreadingapp.TarotApi.Response

data class Card(
    val desc: String,
    val meaning_rev: String,
    val meaning_up: String,
    val name: String,
    val name_short: String,
    val suit: String?,
    val type: String,
    val value: String,
    val value_int: Int
)