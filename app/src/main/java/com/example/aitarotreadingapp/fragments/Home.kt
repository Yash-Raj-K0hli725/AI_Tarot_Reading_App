package com.example.aitarotreadingapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.MVP.Contract
import com.example.aitarotreadingapp.MVP.presenter.Presenter
import com.example.aitarotreadingapp.R
import com.example.aitarotreadingapp.TarotApi.Response.Card
import com.example.aitarotreadingapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Home : Fragment(), Contract.view {
    lateinit var bind: FragmentHomeBinding
    lateinit var dataBase: cardsDataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        dataBase = cardsDataBase.getInstance(requireActivity())
        val presenter = Presenter()
        presenter.onAttach(this)
        val query = bind.query
        bind.btnSearch.setOnClickListener {
            if (query.text.isNotEmpty()) {
                presenter.onQuestionAsked(requireContext())
                bind.yourCards.visibility = View.VISIBLE
            }
        }

        bind.query.setOnClickListener {
            bind.yourCards.visibility = View.GONE
        }
        // Inflate the layout for this fragment
        return bind.root
    }


    override fun youGot(listofCards: List<CardsData>) {
        bind.btnSearch.isEnabled = false
        val idk = listOf(bind.card1, bind.card2, bind.card3)
        for (i in idk) {
            i.rotationY = 0f
            i.scaleX = 1f
            i.rotation = 0f
            i.clearAnimation()
            i.translationX = 0f
            i.setImageResource(R.drawable.card_back)
            i.setOnClickListener(null)
        }
        bind.yourCards.translationY = 0f
        animateCardStack(listofCards)

    }

    override fun setPopupNavigation(image: ImageView, youGot: CardsData) {
        image.setOnClickListener {
            requireView().findNavController().navigate(HomeDirections.actionHome2ToPopUp(youGot))
        }

    }

    //sets Image to the cards
    override fun setCardImages(name_short: String, image: ImageView) {
        val imageid = getImageId(name_short)
        image.scaleX = -1f
        image.setImageResource(imageid)
    }

    //finds id of correct image for outcome
    override fun getImageId(name_short: String): Int {
        val context = requireContext()
        return context.resources.getIdentifier(name_short, "raw", context.packageName)
    }

    //AllAnimations
    override fun animate(image: ImageView, youGot: CardsData) {
        image.animate().setDuration(700L).rotationY(180f).start()
        Handler(Looper.getMainLooper()).postDelayed({
            setCardImages(youGot.name_short, image)
            Floating(image)
            setPopupNavigation(image, youGot)
        }, 350)
    }

    override fun animateCardStack(listofCards: List<CardsData>) {
        val cardStack = bind.yourCards
        cardStack.animate().alpha(1f).setDuration(700L)
            .withEndAction {
                //repeated_shuffleAnimation
                CoroutineScope(Dispatchers.Main).launch {
                    for (i in 1..3) {
                        shuffleAnimation()
                        delay(400)
                    }
                    spreadCards()
                    delay(800)
                    bind.btnSearch.isEnabled = true

                    bind.card1.setOnClickListener {
                        it.animate().rotation(10f).setDuration(700L).start()
                        animate(it as ImageView, listofCards[0])
                    }
                    bind.card2.setOnClickListener {
                        animate(it as ImageView, listofCards[1])
                    }
                    bind.card3.setOnClickListener {
                        it.animate().rotation(-10f).setDuration(700L).start()
                        animate(it as ImageView, listofCards[2])
                    }
                }
            }.start()

    }

    override fun spreadCards() {
        val card1 = bind.card1
        val card3 = bind.card3
        val yourCards = bind.yourCards
        card1.animate().translationX(-350f).setDuration(700L).start()
        card3.animate().translationX(350f).setDuration(700L).start()
        yourCards.animate().translationY(-550f).setDuration(700L).start()
    }

    fun Floating(image: ImageView) {
        val floating = AnimationUtils.loadAnimation(requireContext(), R.anim.floating)
        image.startAnimation(floating)
    }

    override fun shuffleAnimation() {
        val card1 = bind.card1
        val card2 = bind.card2
        //repeat
        card1.animate().translationZ(10f).setStartDelay(100L).setDuration(100L).start()
        card1.animate().rotation(-3f).setDuration(100L).start()
        card2.animate().translationX(300f).setDuration(200L).start()
        card2.animate().rotation(8f).setDuration(200L).start()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                card1.animate().translationZ(0f).setStartDelay(100L).setDuration(100L).start()
                card1.animate().rotation(0f).setDuration(100L).start()
                card2.animate().translationX(0f).setDuration(200L).start()
                card2.animate().rotation(0f).setDuration(200L).start()
            }, 200
        )
    }
    override fun onDestroy() {
        Presenter().onDettach()
        super.onDestroy()
    }

}