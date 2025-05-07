package com.example.project

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.project.model.Film
import com.example.project.database.FilmDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class FilmRepository(private val context: Context) {
    private val db = FilmDatabase(context)
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
            db.writableDatabase.execSQL("DELETE FROM Films") // Menghapus data lama untuk menghindari duplikasi
            Log.d(tag, "Old data deleted from database")

            for (i in 0 until response.length()) {
                val filmJson = response.getJSONObject(i)
                val title = filmJson.getString("title")
                val image = filmJson.getString("image")
                val price = filmJson.getInt("price")
                val film = Film(title, image, price)

                if (i == 0) film.image = "res/drawable/poster.png"
                else if (i == 1) film.image = "res/drawable/poster1.png"

                db.insertData(film)
                Log.d(tag, "Inserted into DB: $title, Image: ${film.image}, Price: $price")
            }

            CoroutineScope(Dispatchers.Main).launch {
                function()
                Log.d(tag, "Data fetch and store completed")
            }
        }
    }
}