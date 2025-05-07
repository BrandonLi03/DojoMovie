package com.example.project.page

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.project.R
import com.example.project.database.DatabaseHelper
import com.example.project.databinding.FilmBinding
import com.example.project.model.Film

class FilmPage : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var movieImageView: ImageView
    private lateinit var movieQuantity: EditText
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var binding: FilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(this)
        binding = FilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        titleTextView = findViewById(R.id.tv_detail_title_movie)
        priceTextView = findViewById(R.id.tv_detail_price_movie)
        movieImageView = findViewById(R.id.img_detail_movie)
        movieQuantity = findViewById(R.id.et_detail_quantity_movie)

        val movieId = intent.getIntExtra("movieId", 0)
        val movie = databaseHelper.getSpecificFilm(movieId)
        if (movie != null) {
            titleTextView.text = movie.title
            priceTextView.text = "Rp. " + movie.price.toString()
            Glide.with(this).load(movie.image).into(movieImageView)
        }
        else {
            Log.e("DetailPage", "Movie with ID $movieId not found")
        }

        binding.btnDetailPurchaseMovie.setOnClickListener {
            val quantity = movieQuantity.text.toString().toIntOrNull()
            if (quantity == null || quantity <= 0) {
                Toast.makeText(this, "Ga boleh kosong yak", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                databaseHelper.insertTransaction(movieId, quantity, 1)
                Log.d("TransactionLog", "User 1 membeli Movie ID: $movieId dengan Quantity: $quantity")
                Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainPage::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


}