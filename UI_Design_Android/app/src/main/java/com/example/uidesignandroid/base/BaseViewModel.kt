package com.example.uidesignandroid.base

import androidx.lifecycle.ViewModel
import com.example.uidesignandroid.interfaces.ApiCallBacks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    fun <T : Any> call(loginAPI: Call<T>, apiCallBack: ApiCallBacks) {
        loginAPI.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        apiCallBack.success(it)
                    }
                } else {
                    apiCallBack.failure(true)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                apiCallBack.failure(true)
            }
        })
    }

}