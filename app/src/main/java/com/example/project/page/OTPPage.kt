package com.example.project.page

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.telephony.SmsManager
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
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.R

class OTPPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.otp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val resend = findViewById<TextView>(R.id.resend)
        val text = "Doesn't receive any code? Resend code"
        val spannableString = SpannableString(text)
        val button = findViewById<Button>(R.id.buttonclick)
        val otp = findViewById<EditText>(R.id.otp)
        var code = getRandomString(6)
        var smsManager: SmsManager = SmsManager.getDefault()
        val userId = intent.getIntExtra("USER_ID", -1)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userPhone = sharedPreferences.getString("USER_PHONE", "000")
        CheckPermissionAndSend(userPhone, code)
        otp.setText(code)

        button.setOnClickListener {
            if (otp.text.toString() == code) {
                val intent = Intent(this@OTPPage, MainPage::class.java)
                intent.putExtra("USER_ID", userId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Code is incorrect", Toast.LENGTH_SHORT).show()
            }
        }

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                code = getRandomString(6)
                otp.setText(code)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#FFA600")
            }
        }

        val startIndex = text.indexOf("Resend code")
        val endIndex = startIndex + "Resend code".length

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        resend.text = spannableString

        resend.movementMethod = LinkMovementMethod.getInstance()

    }

    fun CheckPermissionAndSend(phoneNumber : String?, message : String) {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), 1)
        } else {
            SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val userPhone = sharedPreferences.getString("USER_PHONE", "000")
            val code = getRandomString(6)
            SmsManager.getDefault().sendTextMessage(userPhone, null, code, null, null)
        } else {
            Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}