package com.example.aitarotreadingapp.fragments.History

import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aitarotreadingapp.DataBase.Dao
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.MVP.model.mainModel
import com.example.aitarotreadingapp.R
import com.example.aitarotreadingapp.databinding.FragmentHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class History : Fragment() {
    lateinit var bind:FragmentHistoryBinding
    lateinit var dataBase: Dao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_history,container,false)
        dataBase = cardsDataBase.getInstance(requireContext()).getDB()
        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = listAdapter()
        listAdapter.getContext(requireContext())
        bind.HistoryRecycle.adapter = listAdapter
        bind.HistoryRecycle.layoutManager = LinearLayoutManager(requireContext())
        dataBase.liveReadAll().observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }

        bind.clearAll.setOnClickListener {
           CoroutineScope(Dispatchers.IO).launch {
               dataBase.deleteAllQueries()
           }
        }
    }
}