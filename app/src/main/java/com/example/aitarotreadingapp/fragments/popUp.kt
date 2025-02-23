package com.example.aitarotreadingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.aitarotreadingapp.R
import com.example.aitarotreadingapp.databinding.FragmentPopUpBinding

class popUp : DialogFragment() {
    lateinit var bind:FragmentPopUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialog?.window?.apply {
            // Remove default background of PopUp
            setBackgroundDrawableResource(android.R.color.transparent)}

        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_pop_up,container,false)
        val card = popUpArgs.fromBundle(requireArguments()).card
        bind.name.text = card.name
        bind.desc.text = card.meaning_up
        bind.card.setImageResource(getImageId(card.name_short))
        // Inflate the layout for this fragment
        return bind.root
    }

    //finds id of correct image for outcome
     fun getImageId(name_short: String): Int {
        val context = requireContext()
        return context.resources.getIdentifier(name_short, "raw", context.packageName)
    }

}