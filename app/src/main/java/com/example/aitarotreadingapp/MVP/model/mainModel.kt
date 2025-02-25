package com.example.aitarotreadingapp.MVP.model

import android.content.Context
import android.util.Log
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.DataBase.PreviousQueries
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.MVP.Contract
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class mainModel(context: Context):Contract.modelImpl {
    private val dataBase = cardsDataBase.getInstance(context).getDB()

    private val Gemini = GenerativeModel(
        apiKey = com.example.aitarotreadingapp.BuildConfig.SECRET_API_KEY ,
        modelName = "gemini-2.0-flash"
    )

    //gives list of CardsData we feeded to DataBase from API
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

     override suspend fun generateAiResponse(cards: String,question:String):String {
        val response = CoroutineScope(Dispatchers.IO).async{
            val systemInstruction = "I will name you 3 tarot cards and a question give me my prediction only in short"
            val response = Gemini.generateContent(content(role = "user") {
                text(systemInstruction)
                text(cards)
                text(question) })
                response.text?.let {
                    Log.d("yash", "data : $it")
            }
            response.text!!
        }
        return response.await()
    }
}