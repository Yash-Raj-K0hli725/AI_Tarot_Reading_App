package com.example.aitarotreadingapp

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aitarotreadingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(bind.bottomNavigation) { v, inset ->
            val navBar = inset.getInsets(WindowInsetsCompat.Type.navigationBars())
            bind.main.updatePadding(bottom = navBar.bottom)
            inset
        }

        val navHost = findNavController(R.id.hostHolder)
        bind.crystalBall.setOnClickListener {
            animateCrystalBall()
            it.setOnClickListener(null)
            bind.clickText.animate().alpha(0f).setDuration(600L).withEndAction {
                bind.clickText.visibility = View.GONE
            }.start()
            bind.bgClickText.animate().alpha(0f).setDuration(600L).withEndAction {
                bind.bgClickText.visibility = View.GONE
            }.start()
        }

        bind.bottomNavigation.setupWithNavController(navHost)

        //Handling Backpress
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navHost.popBackStack()) {
                    finish()
                }
            }
        })
    }

    private fun animateCrystalBall() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(bind.main)
        constraintSet.apply {
            setVerticalBias(bind.crystalBall.id, 0.93f)
            TransitionManager.beginDelayedTransition(bind.main, ChangeBounds().apply {
                duration = 400
            })
            constraintSet.applyTo(bind.main)
            bind.crystalBall.animate().scaleX(1f).scaleY(1f).setDuration(400).start()
        }

    }

}