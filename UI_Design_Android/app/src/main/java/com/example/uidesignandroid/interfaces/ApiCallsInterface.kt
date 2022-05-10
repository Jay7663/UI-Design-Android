package com.example.uidesignandroid.interfaces

interface ApiCallsInterface {
    fun <T> success(responseData: T)
    fun failure(message: String)
}