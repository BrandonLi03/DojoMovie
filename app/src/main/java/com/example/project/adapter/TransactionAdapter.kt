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

class TransactionAdapter(private val TransactionList: ArrayList<Transaction>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var movieImage = itemView.findViewById<ImageView>(R.id.img_transaction_movie)
        private var movieTitle = itemView.findViewById<TextView>(R.id.tv_transaction_movie_title)
        private var moviePrice = itemView.findViewById<TextView>(R.id.tv_transaction_movie_price)
        private var movieQuantity = itemView.findViewById<TextView>(R.id.tv_transaction_movie_quantity)
        private var movieTotal = itemView.findViewById<TextView>(R.id.tv_transaction_movie_total)

        fun bind(transaction: Transaction) {
            movieTitle.text = transaction.film_title
            moviePrice.text = "Price : " + "Rp. " + transaction.film_price.toString()
            movieQuantity.text = "Quantity : " + transaction.quantity.toString()
            movieTotal.text = "Total : " + "Rp. ${(transaction.film_price ?: 0) * (transaction.quantity ?: 0)}"

            Glide.with(itemView.context).load(transaction.film_image).into(movieImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.film_history_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return TransactionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(TransactionList[position])
    }
}
