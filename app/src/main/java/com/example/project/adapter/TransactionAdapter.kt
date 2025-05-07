package com.example.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.R
import com.example.project.model.Transaction

class TransactionAdapter(private val TransactionList: ArrayList<Transaction>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(item: Transaction)
    }

    class ViewHolder(itemView: View, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        // tempat untuk binding dengan komponen yang ada di layout
        private var movieImage = itemView.findViewById<ImageView>(R.id.img_transaction_movie)
        private var movieTitle = itemView.findViewById<TextView>(R.id.tv_transaction_movie_title)
        private var moviePrice = itemView.findViewById<TextView>(R.id.tv_transaction_movie_price)
        private var movieQuantity = itemView.findViewById<TextView>(R.id.tv_transaction_movie_quantity)
        private var movieTotal = itemView.findViewById<TextView>(R.id.tv_transaction_movie_total)
        private var cardView = itemView.findViewById<CardView>(R.id.transaction_movie_cardview)

        fun bind(transaction: Transaction) {
            movieTitle.text = transaction.film_title
            moviePrice.text = "Price : " + "Rp. " + transaction.film_price.toString()
            movieQuantity.text = "Quantity : " + transaction.quantity.toString()
            movieTotal.text = "Total : " + "Rp. ${(transaction.film_price ?: 0) * (transaction.quantity ?: 0)}"

            Glide.with(itemView.context).load(transaction.film_image).into(movieImage)

            cardView.setOnClickListener {
                listener.onItemClick(transaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // tempat untuk memilih sebuah layout untuk data movienya
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.film_history_item_layout, parent, false)
        return ViewHolder(itemView, itemClickListener)
    }

    override fun getItemCount(): Int {
        return TransactionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // tempat untuk set data di komponen yang ada di layout
        holder.bind(TransactionList[position])
    }
}
