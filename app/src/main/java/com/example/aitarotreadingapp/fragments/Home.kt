package com.example.aitarotreadingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.aitarotreadingapp.DataBase.cardsDataBase
import com.example.aitarotreadingapp.R
import com.example.aitarotreadingapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Home : Fragment() {
    lateinit var bind:FragmentHomeBinding
    lateinit var dataBase:cardsDataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        dataBase = cardsDataBase.getInstance(requireActivity())
        bind.btnSearch.setOnClickListener {
            if(bind.edit.text.isNotEmpty()){
               CoroutineScope(Dispatchers.Main).launch {
                   searchQuery()
               }
            }
        }
        // Inflate the layout for this fragment
        return bind.root
    }

    suspend fun searchQuery(){
       val query =  bind.edit.text.toString()
        var result:String = ""
        val job = CoroutineScope(Dispatchers.IO).launch {
           result  = dataBase.getDB().Read(query).toString()
        }
        job.join()
        bind.result.text  = result
    }

}