package com.example.aitarotreadingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.aitarotreadingapp.DataBase.CardsData
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.TarotApi.ApiService
import com.example.aitarotreadingapp.TarotApi.Response.Card
import com.example.aitarotreadingapp.TarotApi.Response.Details
import com.example.aitarotreadingapp.TarotApi.retroTarot
import com.example.aitarotreadingapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//This Activity is used for Splash Screen
class SplashActivity : AppCompatActivity() {
    lateinit var bind: ActivitySplashBinding
    lateinit var dataBase: cardsDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bind = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        dataBase = cardsDataBase.getInstance(this)
        checkDataBase()
        animateSplashText()
    }

    fun animateSplashText() {
        bind.awaits.animate().alpha(1f).setStartDelay(400L).setDuration(700).start()
        bind.dot1.animate().alpha(1f).setStartDelay(600L).setDuration(500).start()
        bind.dot2.animate().alpha(1f).setStartDelay(800L).setDuration(500).start()
        bind.dot3.animate().alpha(1f).setStartDelay(900L).setDuration(500).start()
    }

    fun switchToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    fun checkDataBase() {
        CoroutineScope(Dispatchers.IO).launch {
            val entryCount = dataBase.getDB().Count()
            if (entryCount == 0 || entryCount < 78) {
                importData()
            }
            else{
                Handler(Looper.getMainLooper()).postDelayed({
                    switchToMainActivity()
                },2000)
            }
        }
    }

    private fun importData() {

        val tarotDetails = retroTarot.getInstance().create(ApiService::class.java)
        tarotDetails.getCardsDetails().enqueue(object : Callback<Details> {

            override fun onResponse(p0: Call<Details>, response: Response<Details>) {
                Log.d("yash", "connection to tarrot Api : ${response.isSuccessful}")
                val cardDetails = response.body()!!.cards
                CoroutineScope(Dispatchers.Main).launch {
                    val job  = CoroutineScope(Dispatchers.IO).launch{
                        saveToDatabase(cardDetails)
                    }
                    job.join()
                    switchToMainActivity()
                }

            }

            override fun onFailure(p0: Call<Details>, error: Throwable) {
                Log.e("error", "error is -> $error")
                networkErrorAlert()
            }
        })
    }

    private fun networkErrorAlert() {
        AlertDialog.Builder(this@SplashActivity)
            .setTitle("Network Error")
            .setMessage("Please Check your Internet Connection and Try Again")
            .setPositiveButton("Retry") { _, _ ->
                importData()
            }
            .setNegativeButton("Close") { _, _ ->
                finish()
            }.show()
    }

    private suspend fun saveToDatabase(cardDetails:List<Card>){
       val mappedCards = cardDetails.map {
            CardsData(
                desc = it.desc,
                meaning_rev = it.meaning_rev,
                meaning_up = it.meaning_up,
                name = it.name,
                name_short = it.name_short,
                suit = it.suit,
                type = it.type,
            )
        }
            dataBase.getDB().InsertAllTarotDetails(mappedCards)
    }

}