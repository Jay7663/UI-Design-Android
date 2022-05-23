package com.example.uidesignandroid.viewmodels

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.example.uidesignandroid.base.BaseViewModel
import com.example.uidesignandroid.interfaces.ApiCall
import com.example.uidesignandroid.interfaces.ApiCallBacks
import com.example.uidesignandroid.models.LogInRequest

class SignUpViewModel : BaseViewModel(), ApiCallBacks {
    val isValidUser = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()

    fun signUp(context: Activity, email: String, password: String) {
        val apiInterface = ApiCall.getRetrofitObject(context).create(ApiCall::class.java)
        val signUpRequest = LogInRequest(email, password)
        val loginAPI = apiInterface.signUpWithRetrofit(signUpRequest)
        call(loginAPI, this@SignUpViewModel)
    }

    override fun <T> success(data: T) {
        isValidUser.postValue(true)
    }

    override fun failure(didFailed: Boolean) {
        isValidUser.postValue(false)
    }
}