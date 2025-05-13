package com.example.project.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.adapter.Adapter
import com.example.project.database.DatabaseHelper
import com.example.project.FilmRepository
import com.example.project.R
import com.example.project.model.Film
import com.example.project.page.FilmPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class HomeFragment : Fragment() {
    private lateinit var list: RecyclerView
    private lateinit var dataList: ArrayList<Film>
    private lateinit var db: DatabaseHelper
    private lateinit var adapter: Adapter
    private lateinit var repository: FilmRepository
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        userId = arguments?.getInt("USER_ID", -1) ?: -1

        list = view.findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(requireContext())

        db = DatabaseHelper(requireContext())
        repository = FilmRepository(requireContext())

        dataList = ArrayList()
        adapter = Adapter(dataList, object : Adapter.OnItemClickListener {
            override fun onItemClick(film: Film) {
                val intent = Intent(requireContext(), FilmPage::class.java)
                intent.putExtra("filmId", film.id)
                intent.putExtra("USER_ID", userId)
                startActivity(intent)
            }
        })

        list.adapter = adapter

        fetchDataAndDisplay()

        return view
    }

    private fun fetchDataAndDisplay() {
        repository.fetchAndStoreFilms {
            getData()
        }
        val films = db.getFilms()
        Log.d("HomeFragment", if (films.isNotEmpty()) "Film ada: ${films.size} data ditemukan" else "Tidak ada data film")
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            val films = db.getFilms()

            if (films.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    dataList.clear()
                    dataList.addAll(films)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }


    private fun getBitmapFromPath(imagePath: String): Bitmap? {
        val file = File(imagePath)
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }
}
