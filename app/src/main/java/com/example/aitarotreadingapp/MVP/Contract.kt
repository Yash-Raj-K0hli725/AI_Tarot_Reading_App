package com.example.aitarotreadingapp.MVP

import android.content.Context
import android.widget.ImageView
import com.example.aitarotreadingapp.DataBase.CardsData

interface Contract {
    interface modelImpl {
        suspend fun fetchDetails(context: Context):List<CardsData>?
    }

    interface presenter {
        fun onAttach(view:view)
        fun onDettach()
        fun onQuestionAsked(context: Context)
        fun giveCards(cards: List<CardsData>): List<CardsData>

    }

    interface view{
        fun youGot(listofCards:List<CardsData>)
        fun setCardImages(name_short: String,image: ImageView)
        fun getImageId(name_short: String): Int
        fun animate(image: ImageView, youGot: CardsData)
        fun setPopupNavigation(image: ImageView, youGot: CardsData)
        fun animateCardStack(listofCards: List<CardsData>)
        fun spreadCards()
        fun shuffleAnimation()
    }
}