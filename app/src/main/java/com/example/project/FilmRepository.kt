package com.example.project

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.project.database.DatabaseHelper
import com.example.project.model.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class FilmRepository(private val context: Context) {
    private val db = DatabaseHelper(context)
    private val tag = "FilmRepository"

    fun fetchAndStoreFilms(function: () -> Unit) {
        val url = "https://api.npoint.io/66cce8acb8f366d2a508"

        val requestQueue = Volley.newRequestQueue(context)

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                Log.d(tag, "API Fetch Success: ${response.length()} items received")
                saveDataToDatabase(response, function)
            },
            { error -> Log.e(tag, "Error fetching API: ${error.message}") }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun saveDataToDatabase(response: JSONArray, function: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until response.length()) {
                val filmJson = response.getJSONObject(i)
                val title = filmJson.getString("title")
                val image = filmJson.getString("image")
                val price = filmJson.getInt("price")

                val film = Film(title, image, price)

                if (i == 0) film.image = "poster"
                else if (i == 1) film.image = "poster1"
                else if (i == 2) film.image = "poster2"

                if (!db.isFilmExists(title)) {
                    db.insertFilm(film)
                    Log.d(tag, "Inserted: $title")
                } else {
                    Log.d(tag, "Film already exists: $title")
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
                function()
                Log.d(tag, "Data fetch and store completed")
            }
        }
    }
}