package com.example.uidesignandroid.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.uidesignandroid.interfaces.ApiCallsInterface
import com.example.uidesignandroid.viewmodels.ApiError
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

open class BaseViewModel : ViewModel() {

    fun <T : Any> call(
        method: String,
        url: String,
        requestedCode: Int,
        body: JSONObject?,
        model: Class<T>,
        apiInterface: ApiCallsInterface
    ) {
        val requestURL = URL(url)
        val objectOfJson: T
        with(requestURL.openConnection() as HttpURLConnection) {
            requestMethod = method
            setRequestProperty("Content-Type", "application/json")

            body?.let {
                val wr = OutputStreamWriter(outputStream)
                wr.write(body.toString())
                wr.flush()
            }

            try {
                when (responseCode) {
                    requestedCode -> {
                        BufferedReader(InputStreamReader(inputStream)).use {
                            val response = StringBuffer()
                            var inputLine = it.readLine()
                            while (inputLine != null) {
                                response.append(inputLine)
                                inputLine = it.readLine()
                            }
                            val gson = Gson()
                            objectOfJson = gson.fromJson(response.toString(), model)
                            Log.d("API", objectOfJson.toString())
                        }
                        apiInterface.success(objectOfJson)
                    }
                    in 399..600 -> {
                        BufferedReader(InputStreamReader(errorStream)).use {
                            val error = Gson().fromJson(it.readLine(), ApiError::class.java)
                            apiInterface.failure(error.error)
                        }
                    }
                }
            } catch (ex: Exception) {
                apiInterface.failure("Failed !!! Please try again")
            }
        }
    }

}