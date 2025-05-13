package com.example.project.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.model.Film
import java.io.File

class Adapter(
    private val filmList: List<Film>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter.ViewHolderClass>() {

    interface OnItemClickListener {
        fun onItemClick(film: Film)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val film = filmList[position]

        val file = File(film.image)
        val bitmap = if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
        holder.image.setImageBitmap(bitmap)

        holder.title.text = film.title
        holder.price.text = film.price.toString()

        holder.cardView.setOnClickListener {
            listener.onItemClick(film)
        }
    }

    override fun getItemCount(): Int = filmList.size

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img_movie)
        val title: TextView = itemView.findViewById(R.id.tv_movie_title)
        val price: TextView = itemView.findViewById(R.id.tv_movie_price)
        val cardView: CardView = itemView.findViewById(R.id.film_cardview)
    }
}
