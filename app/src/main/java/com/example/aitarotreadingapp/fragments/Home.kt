package com.example.aitarotreadingapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.R
import com.example.aitarotreadingapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class Home : Fragment() {
    lateinit var bind: FragmentHomeBinding
    lateinit var dataBase: cardsDataBase
    lateinit var flip:Animation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        dataBase = cardsDataBase.getInstance(requireActivity())
        flip = AnimationUtils.loadAnimation(requireContext(),R.anim.flip)
        bind.btnSearch.setOnClickListener {
           CoroutineScope(Dispatchers.Main).launch {
               Random()
           }
            bind.yourCards.visibility = View.VISIBLE
        }

        bind.query.setOnClickListener {
            bind.yourCards.visibility = View.GONE
        }
        // Inflate the layout for this fragment
        return bind.root
    }

    suspend fun Random() {
        //val Query =  bind.query.text.toString()
        var listofCards:List<CardsData>? = null
            val job = CoroutineScope(Dispatchers.IO).launch {
                listofCards= dataBase.getDB().readAll()
            }
        job.join()
        if(listofCards!=null){
            val youGot = giveCards(listofCards!!)
            val idk = listOf(bind.card1,bind.card2,bind.card3)
            for (i in idk){
                i.rotationY = 0f
                i.scaleX = 1f
                i.rotation = 0f
                i.setImageResource(R.drawable.card_back)
            }
            bind.card1.setOnClickListener {
                it.animate().rotation(10f).setDuration(700L).start()
                animate(it as ImageView,youGot[0])
            }
            bind.card2.setOnClickListener {
                animate(it as ImageView,youGot[1])
            }

            bind.card3.setOnClickListener {
                it.animate().rotation(-10f).setDuration(700L).start()
                animate(it as ImageView , youGot[2])
            }
        }
    }

    fun animate(image:ImageView,youGot:CardsData){
        image.animate().setDuration(700L).rotationY(180f).start()
        Handler(Looper.getMainLooper()).postDelayed({
            setCardImages(youGot.name_short,image)
        },350)
    }

    fun giveCards(cards: List<CardsData>): List<CardsData> {
        val random = cards.shuffled() as MutableList<CardsData>
        val value = mutableListOf<CardsData>()

        for (i in 0 until 3) {
            value.add(random.first())
            random.removeAt(0)
        }
        return value
    }

    //sets Image to the cards
    fun setCardImages(name_short: String,image:ImageView) {
        val imageid = getImageId(name_short)
        image.scaleX = -1f
        image.setImageResource(imageid)
    }

    //finds id of correct image for outcome
    fun getImageId(name_short: String): Int {
        val context = requireContext()
        return context.resources.getIdentifier(name_short, "raw", context.packageName)
    }

}