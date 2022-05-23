package com.example.uidesignandroid.interfaces

import android.app.Activity
import com.example.uidesignandroid.models.LogInRequest
import com.example.uidesignandroid.models.LogInResponse
import com.example.uidesignandroid.models.SignUpResponse
import com.example.uidesignandroid.utils.ErrorInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCall {
    @POST("api/login")
    fun logInWithRetrofit(@Body loginRequest: LogInRequest): Call<LogInResponse>

    @POST("api/register")
    fun signUpWithRetrofit(@Body loginRequest: LogInRequest): Call<SignUpResponse>

    companion object {
        fun getRetrofitObject(context: Activity): Retrofit {

            val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ErrorInterceptor(context))
            val okHttpClient = okHttpClientBuilder.build()

            return Retrofit.Builder().baseUrl("https://reqres.in/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}