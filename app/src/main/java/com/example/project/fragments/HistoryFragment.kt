package com.example.project.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.adapter.TransactionAdapter
import com.example.project.database.DatabaseHelper
import com.example.project.databinding.HistoryFragmentBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: HistoryFragmentBinding
    private lateinit var transactionRecyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var transactionAdapter: TransactionAdapter
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        userId = arguments?.getInt("USER_ID", -1) ?: -1

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
        transactionRecyclerView = binding.transactionRecyclerView

        setUpRecycler()
    }

    private fun setUpRecycler() {
        val arrayList = databaseHelper.getTransaction(userId)
        Log.d("DEBUG", "Total movies: ${arrayList.size} user: $userId")
        transactionAdapter = TransactionAdapter(arrayList)
        transactionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        transactionRecyclerView.adapter = transactionAdapter
    }
}
