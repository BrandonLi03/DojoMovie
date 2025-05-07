package com.example.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.Items
import com.example.project.R

class Adapter(private val dataList: ArrayList<Items>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<Adapter.ViewHolderClass>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolderClass(itemView, itemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.image.setImageBitmap(currentItem.image) // Use setImageBitmap instead of setImageResource
        holder.text.text = currentItem.title
    }

    class ViewHolderClass(itemView: View, itemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img_movie)
        val text: TextView = itemView.findViewById(R.id.tv_movie_title)
        val price: TextView = itemView.findViewById(R.id.tv_movie_price)
        private val cardView = itemView.findViewById<CardView>(R.id.film_cardview)

        init {
            cardView.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
        }
    }
}
