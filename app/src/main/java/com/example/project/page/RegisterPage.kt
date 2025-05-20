package com.example.project.page

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
import com.example.project.model.User

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registerButton = findViewById<Button>(R.id.buttonclick)
        val phone = findViewById<EditText>(R.id.inputPhone)
        val password = findViewById<EditText>(R.id.inputPassword)
        val confPassword = findViewById<EditText>(R.id.confPassword)
        val regToLog = findViewById<TextView>(R.id.regToLog)
        val text = "Already has an account? Log in here"
        val spannableString = SpannableString(text)
        val context = this

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterPage, LoginPage::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#00B9AE")
            }
        }

        val startIndex = text.indexOf("Log in here")
        val endIndex = startIndex + "Log in here".length

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        regToLog.text = spannableString

        regToLog.movementMethod = LinkMovementMethod.getInstance()

        registerButton.setOnClickListener {
            val len = password.text.length
            val check1 = password.text.toString()
            val check2 = confPassword.text.toString()
            if (len < 8) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT)
                    .show()
            } else if (check1 != check2) {
                Toast.makeText(this, "Passwords are not the same", Toast.LENGTH_SHORT).show()
            } else {
                var user = User(phone.text.toString(), check1)
                var db = DatabaseHelper(context)
                db.insertUser(user)

                val intent = Intent(this@RegisterPage, LoginPage::class.java)
                startActivity(intent)
            }
        }
    }
}