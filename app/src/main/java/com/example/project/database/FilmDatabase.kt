package com.example.project.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.project.model.Film

class FilmDatabase (var context: Context) : SQLiteOpenHelper(context, db_name, null, 4){
    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "CREATE TABLE $table_name ($col_id INTEGER PRIMARY KEY AUTOINCREMENT, $col_title VARCHAR(255), $col_image TEXT, $col_price INTEGER)"
        db?.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Films")
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys = ON;")
    }

    fun insertData(film: Film) {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put(col_image, film.image)
            put(col_price, film.price)
            put(col_title, film.title)
        }

        val result = db.insert(table_name, null, cv)

        // Jalankan Toast di UI thread
        (context as? android.app.Activity)?.runOnUiThread {
            if (result == -1L) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun readData() : MutableList<Film>{
        var list : MutableList<Film> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $table_name"
        var result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var film = Film()
                film.id = result.getString(0).toInt()
                film.title = result.getString(1)
                film.image = result.getString(2)
                film.price = result.getString(3).toInt()
                list.add(film)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    companion object {
        private const val db_name = "DoJo_Movie"
        private const val table_name = "Films"
        private const val col_title = "title"
        private const val col_image = "image"
        private const val col_id = "id"
        private const val col_price = "price"
    }
}