package com.example.project.page

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.database.DatabaseHelper
import com.example.project.R

class LoginPage : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton = findViewById<Button>(R.id.buttonclick)
        val phone = findViewById<EditText>(R.id.inputPhone)
        val password = findViewById<EditText>(R.id.inputPassword)
        val regToLog = findViewById<TextView>(R.id.regToLog)
        val text = "Didn't have an account? Register here!"
        val spannableString = SpannableString(text)
        val context = this
        val db = DatabaseHelper(context)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginPage, RegisterPage::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#FFA600")
            }
        }

        val startIndex = text.indexOf("Register here!")
        val endIndex = startIndex + "Register here!".length

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        regToLog.text = spannableString

        regToLog.movementMethod = LinkMovementMethod.getInstance()

        loginButton.setOnClickListener {
            var data = db.getUser()
            val user = data.find { it.phone == phone.text.toString() && it.password == password.text.toString() }
            val checkphone = data.find { it.phone == phone.text.toString() }

            if (user != null) {
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("USER_PHONE", user.phone)
                editor.apply()

                val intent = Intent(this@LoginPage, OTPPage::class.java)
                intent.putExtra("USER_ID", user.id)
                startActivity(intent)
            }
            else if (checkphone != null) {
                Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Phone number is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}