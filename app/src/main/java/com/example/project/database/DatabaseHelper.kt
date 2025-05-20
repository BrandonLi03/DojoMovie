package com.example.project.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.project.model.Film
import com.example.project.model.Transaction
import com.example.project.model.User

class DatabaseHelper(var context: Context): SQLiteOpenHelper(context, "Dojo_Movie", null, 1) {
    fun createFilmTable(db: SQLiteDatabase?) {
        val create_film_table = "CREATE TABLE IF NOT EXISTS Films(filmId INTEGER PRIMARY KEY AUTOINCREMENT, filmTitle VARCHAR(255), filmImage TEXT, filmPrice INTEGER)"
        db?.execSQL(create_film_table)
    }

    fun createTransactionTable(db: SQLiteDatabase?) {
        val create_transaction_table = "CREATE TABLE IF NOT EXISTS Transactions(Id INTEGER PRIMARY KEY AUTOINCREMENT, filmId INTEGER, Quantity INTEGER, userId INTEGER, FOREIGN KEY(userId) REFERENCES Users(userId) ON DELETE CASCADE, FOREIGN KEY(filmId) REFERENCES Films(filmId) ON DELETE CASCADE)"
        db?.execSQL(create_transaction_table)
    }

    fun createUserTable(db: SQLiteDatabase?) {
        val create_user_table = "CREATE TABLE IF NOT EXISTS Users(userId INTEGER PRIMARY KEY AUTOINCREMENT, phone VARCHAR(30), password VARCHAR(256))"
        db?.execSQL(create_user_table)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        createUserTable(db)
        createFilmTable(db)
        createTransactionTable(db)
    }

    fun insertTransaction(filmId: Int, Quantity: Int, userId: Int){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("userId", userId)
        cv.put("filmId", filmId)
        cv.put("Quantity", Quantity)
        var result = db.insert("Transactions", null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertUser(user: User){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("phone", user.phone)
        cv.put("password", user.password)
        var result = db.insert("Users", null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertFilm(film: Film) {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put("filmImage", film.image)
            put("filmPrice", film.price)
            put("filmTitle", film.title)
        }

        val result = db.insert("Films", null, cv)

        (context as? android.app.Activity)?.runOnUiThread {
            if (result == -1L) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getUser() : MutableList<User>{
        var list : MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM Users"
        var result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var user = User()
                user.id = result.getString(0).toInt()
                user.phone = result.getString(1)
                user.password = result.getString(2)
                list.add(user)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun getFilms() : MutableList<Film>{
        var list : MutableList<Film> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM Films"
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

    fun getSpecificFilm(filmId: Int): Film? {
        val db = readableDatabase
        val query = "SELECT * FROM Films WHERE filmId = ?"
        val cursor = db.rawQuery(query, arrayOf(filmId.toString()))

        var film: Film? = null

        if (cursor.moveToFirst()) {
            film = Film().apply {
                title = cursor.getString(cursor.getColumnIndexOrThrow("filmTitle"))
                image = cursor.getString(cursor.getColumnIndexOrThrow("filmImage"))
                price = cursor.getInt(cursor.getColumnIndexOrThrow("filmPrice"))
            }
        }

        cursor.close()
        db.close()
        return film
    }

    fun getTransaction(userId: Int) : ArrayList<Transaction> {
        val Transaction = ArrayList<Transaction>()

        val db = readableDatabase
        val query = "select Films.filmId, filmTitle, filmImage, filmPrice, Transactions.Quantity from Films " +
                "join Transactions on Films.filmId = Transactions.filmId " +
                "where Transactions.userId = ?"

        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        if (cursor.moveToFirst()){
            do {
                val transaction = Transaction().apply {
                    film_id = cursor.getInt(cursor.getColumnIndexOrThrow("filmId"))
                    film_title = cursor.getString(cursor.getColumnIndexOrThrow("filmTitle"))
                    film_price = cursor.getInt(cursor.getColumnIndexOrThrow("filmPrice"))
                    film_image = cursor.getString(cursor.getColumnIndexOrThrow("filmImage"))
                    quantity = cursor.getInt(cursor.getColumnIndexOrThrow("Quantity"))
                }
                Transaction.add(transaction)
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return Transaction
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Films")
        db?.execSQL("drop table if exists Transactions")
        db?.execSQL("drop table if exists Users")
        onCreate(db)
    }

    fun isFilmExists(title: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Films WHERE filmTitle = ?", arrayOf(title))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
}