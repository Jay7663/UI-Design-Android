package com.example.uidesignandroid.utils

import android.app.Activity
import com.example.uidesignandroid.R
import com.example.uidesignandroid.base.BaseActivity
import com.example.uidesignandroid.models.ErrorResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor(val context: Activity) : Interceptor, BaseActivity() {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response =
            chain.proceed(chain.request()).newBuilder().header("Content-Type", "application/json")
                .build()

        when (response.code()) {
            in 399..600 -> {
                response.body()?.let { responseBody ->
                    val error = responseBody.byteStream().bufferedReader().use {
                        it.readText()
                    }
                    val message = Gson().fromJson(error, ErrorResponse::class.java)
                    runOnUiThread {
                        showAlert(context, context.getString(R.string.error), message.error)
                    }
                }
            }
        }

        return response
    }
}