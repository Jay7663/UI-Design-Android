package com.example.uidesignandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.uidesignandroid.base.BaseViewModel
import com.example.uidesignandroid.interfaces.ApiCallsInterface
import org.json.JSONObject
import java.net.HttpURLConnection

class SignInViewModel : BaseViewModel(), ApiCallsInterface {

    var errorMessage = MutableLiveData<String>()
    var isValidUser = MutableLiveData<Boolean>()

    fun <T : Any> logIn(
        email: String,
        password: String,
        model: Class<T>,
    ) {
        val requestBody = JSONObject()
        requestBody.put("email", email)
        requestBody.put("password", password)

        call(
            "POST",
            "https://reqres.in/api/register",
            HttpURLConnection.HTTP_OK,
            requestBody,
            model,
            this
        )
    }

    override fun <T> success(responseData: T) {
        isValidUser.postValue(true)

    }

    override fun failure(message: String) {
        isValidUser.postValue(false)
        errorMessage.postValue(message)
    }

}