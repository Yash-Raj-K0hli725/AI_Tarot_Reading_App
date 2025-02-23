package com.example.aitarotreadingapp.DataBase

import androidx.room.TypeConverter
import androidx.room.TypeConverters


class Converters {
    @TypeConverter
    fun fromListToString(list:List<String>):String{
        return list.joinToString(",")
    }
    @TypeConverter
    fun fromListToString(value:String):List<String>{
        return value.split(",")
    }
}