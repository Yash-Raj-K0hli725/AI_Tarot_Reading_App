package com.example.aitarotreadingapp.MVP.presenter

import android.content.Context
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.MVP.Contract
import com.example.aitarotreadingapp.MVP.model.mainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Presenter:Contract.presenter {
    private var view:Contract.view? = null

    override fun onAttach(view: Contract.view) {
        this.view = view
    }

    override fun onDettach() {
        this.view = null
    }

    override fun onQuestionAsked(context:Context) {
        if(view!=null){
            CoroutineScope(Dispatchers.Main).launch {
                val listofCards = withContext(Dispatchers.IO){
                    mainModel().fetchDetails(context)
                }
                if(listofCards!=null){
                    val youGot = giveCards(listofCards)
                    view!!.youGot(youGot)
                }
            }
        }
    }
    //Give three random cards
    override fun giveCards(cards: List<CardsData>): List<CardsData> {
        val random = cards.shuffled() as MutableList<CardsData>
        val value = mutableListOf<CardsData>()
        for (i in 0 until 3) {
            value.add(random.first())
            random.removeAt(0)
        }
        return value
    }
}