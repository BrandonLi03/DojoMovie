package com.example.project.page

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project.R
import com.example.project.database.DatabaseHelper
import com.example.project.databinding.FilmBinding

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

        val filmId = intent.getIntExtra("filmId", 0)
        val userId = intent.getIntExtra("USER_ID", -1)
        Log.d("DetailPage", "Received USER_ID: $userId and filmId: $filmId")
        val movie = databaseHelper.getSpecificFilm(filmId)
        if (movie != null) {
            titleTextView.text = movie.title
            priceTextView.text = "Rp. " + movie.price.toString()
        }

        movieQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                priceTextView.text = "Rp. " + (movie?.price?.times(s.toString().toIntOrNull() ?: 0)).toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


        binding.btnDetailPurchaseMovie.setOnClickListener {
            val quantity = movieQuantity.text.toString().toIntOrNull()
            if (quantity == null || quantity <= 0) {
                Toast.makeText(this, "The quantity can't be empty and must be more than 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                databaseHelper.insertTransaction(filmId, quantity, userId)
                Log.d("TransactionLog", "User $userId membeli Movie ID: $filmId dengan Quantity: $quantity")
                Toast.makeText(this, "Purchase success", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainPage::class.java)
                intent.putExtra("USER_ID", userId)
                startActivity(intent)
                finish()
            }
        }
    }


}