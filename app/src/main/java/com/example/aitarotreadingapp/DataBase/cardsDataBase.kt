package com.example.aitarotreadingapp.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
@TypeConverters(Converters::class)
@Database([CardsData::class,PreviousQueries::class], version = 1)

abstract class cardsDataBase:RoomDatabase() {
    abstract fun getDB():Dao
    companion object{
        @Volatile
        private var Instance:cardsDataBase? = null

        fun getInstance(context: Context):cardsDataBase {
            if(Instance ==null){
                synchronized(this){
                    if (Instance == null){
                        Instance = Room
                            .databaseBuilder(
                                context.applicationContext
                                ,cardsDataBase::class.java
                                ,"TarotData")
                            .build()
                    }
                }
            }
            return Instance!!
        }
    }
}