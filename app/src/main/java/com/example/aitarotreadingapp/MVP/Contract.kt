package com.example.aitarotreadingapp.MVP

import android.content.Context
import android.widget.ImageView
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.DataBase.PreviousQueries
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.TarotApi.Response.Card

interface Contract {
    interface modelImpl {
        suspend fun fetchDetails():List<CardsData>?
        suspend fun insertQueryDetails(prevQuery:PreviousQueries)
        suspend fun readAllQueries():List<PreviousQueries>?
        suspend fun deleteAll()
        suspend fun generateAiResponse(cards: String,question:String):String
    }

    interface presenter {
        fun getContext(context: Context)
        fun onAttach(view:view)
        fun onDettach()
        suspend fun checkAskedQuestion(text:String):Boolean
        fun onQuestionAsked(context: Context)
        fun giveCards(cards: List<CardsData>): List<CardsData>
        fun saveQuery(results:List<String>,question:String,AIresponse:String?)
    }

    interface view{
        fun youGot(listofCards:List<String>)
        fun setCardImages(name_short: String,image: ImageView)
        fun getImageId(name_short: String): Int
        fun animate(image: ImageView,youGot: String)
        fun animateCardStack()
        fun spreadCards()
        fun shuffleAnimation()
    }
}