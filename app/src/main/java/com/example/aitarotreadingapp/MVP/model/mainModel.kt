package com.example.aitarotreadingapp.MVP.model

import android.content.Context
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.DataBase.Dao
import com.example.aitarotreadingapp.DataBase.PreviousQueries
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.MVP.Contract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class mainModel(context: Context):Contract.modelImpl {
    val dataBase = cardsDataBase.getInstance(context).getDB()

    override suspend fun fetchDetails(): List<CardsData>? {
        var listofCards:List<CardsData>? = null
        val job = CoroutineScope(Dispatchers.IO).launch {
            listofCards = dataBase.readAll()
        }
        job.join()
        return listofCards
    }

    override suspend fun insertQueryDetails(prevQuery: PreviousQueries) {
        dataBase.InsertQuestion(prevQuery)
    }

    override suspend fun deleteAll() {
        dataBase.deleteAllQueries()
    }

    override suspend fun readAllQueries():List<PreviousQueries>?{
        var value:List<PreviousQueries>? = null
        val job = CoroutineScope(Dispatchers.IO).launch {
            value = dataBase.readAllQueries()
        }
        job.join()
        return value
    }
}