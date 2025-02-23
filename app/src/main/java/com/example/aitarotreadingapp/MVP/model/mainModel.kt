package com.example.aitarotreadingapp.MVP.model

import android.content.Context
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.DataBase.Dao
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.MVP.Contract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class mainModel:Contract.modelImpl {
    lateinit var dataBase: Dao
    override suspend fun fetchDetails(context: Context): List<CardsData>? {
        dataBase = cardsDataBase.getInstance(context).getDB()
        var listofCards:List<CardsData>? = null
        val job = CoroutineScope(Dispatchers.IO).launch {
            listofCards = dataBase.readAll()
        }
        job.join()
        return listofCards
    }
}