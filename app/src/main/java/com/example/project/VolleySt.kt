package com.example.project

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySt private constructor(context: Context) {
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    companion object {
        @Volatile
        private var INSTANCE: VolleySt? = null

        fun getInstance(context: Context): VolleySt =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VolleySt(context).also { INSTANCE = it }
            }
    }
}