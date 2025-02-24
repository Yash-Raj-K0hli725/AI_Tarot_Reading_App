package com.example.aitarotreadingapp.fragments.History

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aitarotreadingapp.DataBase.PreviousQueries
import com.example.aitarotreadingapp.R
import com.example.aitarotreadingapp.databinding.ListItemLayoutBinding

class listAdapter:ListAdapter<PreviousQueries,listAdapter.listVH>(diffUtil()) {
    lateinit var context:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listVH {
        val bind = LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout,parent,false)
        return listVH(bind)
    }

    override fun onBindViewHolder(holder: listVH, position: Int) {
        val currentItem  = getItem(position)
        onBind(holder.itemView,currentItem)
    }

    fun onBind(bind:View,currentItem:PreviousQueries){
        bind.apply {
            findViewById<TextView>(R.id.question).text = currentItem.question
            findViewById<ImageView>(R.id.car1).setImageResource(getImageId(currentItem.prevCards[0]))
            findViewById<ImageView>(R.id.car2).setImageResource(getImageId(currentItem.prevCards[1]))
            findViewById<ImageView>(R.id.car3).setImageResource(getImageId(currentItem.prevCards[2]))
        }
    }

    fun getContext(context: Context){
        this.context = context
    }

     fun getImageId(name_short: String): Int {
        return context.resources.getIdentifier(name_short, "raw", context.packageName)
    }

    inner class listVH( Item: View):RecyclerView.ViewHolder(Item)


}

class diffUtil:DiffUtil.ItemCallback<PreviousQueries>(){

    override fun areItemsTheSame(oldItem: PreviousQueries, newItem: PreviousQueries): Boolean {
       return oldItem.prevCards == newItem.prevCards
    }

    override fun areContentsTheSame(oldItem: PreviousQueries, newItem: PreviousQueries): Boolean {
        return oldItem.question == newItem.question
    }
}