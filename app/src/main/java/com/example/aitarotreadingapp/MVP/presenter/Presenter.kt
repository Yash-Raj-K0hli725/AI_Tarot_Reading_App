package com.example.aitarotreadingapp.MVP.presenter

import android.content.Context
import android.util.Log
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.DataBase.PreviousQueries
import com.example.aitarotreadingapp.MVP.Contract
import com.example.aitarotreadingapp.MVP.model.mainModel
import com.example.aitarotreadingapp.TarotApi.Response.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Presenter : Contract.presenter {
    private var view: Contract.view? = null
    private lateinit var mainModel: mainModel
    override fun onAttach(view: Contract.view) {
        this.view = view
    }

    override fun getContext(context: Context) {
        mainModel = mainModel(context)
    }

    override fun onDettach() {
        this.view = null
    }

    override fun onQuestionAsked(context: Context) {

        if (view != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val listofCards = withContext(Dispatchers.IO) {
                    mainModel.fetchDetails()
                }
                if (listofCards != null) {
                    val temp = giveCards(listofCards)
                    //get your cards name from here for api calls
                    val youGot = mutableListOf<String>()
                    for (i in temp){
                        youGot.add(i.name_short)
                    }
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

    override fun saveQuery(results: List<String>, question: String) {
        val prevQuery = PreviousQueries(question, results, null)

        CoroutineScope(Dispatchers.IO).launch {
            mainModel.insertQueryDetails(prevQuery)
        }
    }

    override suspend fun checkAskedQuestion(text: String): Boolean {
        var flag = true
            val list = mainModel.readAllQueries()
            if (list != null) {
                for (i in list) {
                    if (i.question == text) {
                        flag = false
                        givePrevCards(i)
                        break
                    }
                }
            }
        return flag
    }

    fun givePrevCards(i:PreviousQueries){
            view!!.youGot(i.prevCards)
    }
}