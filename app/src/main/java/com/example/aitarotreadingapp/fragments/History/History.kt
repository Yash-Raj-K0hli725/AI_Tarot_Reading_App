package com.example.aitarotreadingapp.fragments.History

import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aitarotreadingapp.R
import com.example.aitarotreadingapp.databinding.FragmentHistoryBinding

class History : Fragment() {
    lateinit var bind:FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_history,container,false)
        // Inflate the layout for this fragment
        return bind.root
    }
}