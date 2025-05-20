package com.example.project.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import com.example.project.page.LoginPage
import com.example.project.R

class ProfileFragment : Fragment() {

    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.buttonclick)
        var phone = view.findViewById<TextView>(R.id.phone)

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userPhone = sharedPreferences.getString("USER_PHONE", "000")
        phone.text = userPhone

        button.setOnClickListener {
            showPopupWindow(it)
        }
    }

    private fun showPopupWindow(view: View) {
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.fragment_popup_logout, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.animationStyle = android.R.style.Animation_Dialog

        val btnClose = popupView.findViewById<Button>(R.id.btnCancel)
        btnClose.setOnClickListener {
            popupWindow.dismiss()
        }

        val logout = popupView.findViewById<Button>(R.id.btnConfirm)
        logout.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(requireContext(), LoginPage::class.java)
            startActivity(intent)
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
