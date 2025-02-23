package com.example.aitarotreadingapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.aitarotreadingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHost = findNavController(R.id.hostHolder)
        bind.main.post {
            val yMiddle = bind.main.height
            val ball = bind.crystalBall
           ball.translationY = -((yMiddle-200)/ 2).toFloat()
        }

        bind.crystalBall.setOnClickListener {
            it.animate().scaleX(1f).setDuration(700L).start()
            it.animate().scaleY(1f).setDuration(700L).start()
            it.animate().translationY(0f).setDuration(700L).start()
            bind.clickText.animate().alpha(0f).setDuration(600L).withEndAction {
                bind.clickText.visibility = View.GONE
            }.start()
        }

        bind.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navHome-> { navHost.popBackStack(R.id.home,false)
                true
                }
                R.id.navHistory-> {navHost.navigate(R.id.action_home_to_history)
                    true }
                else -> false
            }

        }

        //Handling Backpress
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navHost.popBackStack()) {
                    finish()
                }
            }
        })
    }

    fun setFragment(fragment: Fragment){

    }
}