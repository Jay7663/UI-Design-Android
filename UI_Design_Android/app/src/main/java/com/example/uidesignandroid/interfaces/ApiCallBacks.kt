package com.example.uidesignandroid.interfaces

interface ApiCallBacks {
    fun <T> success(data: T)
    fun failure(didFailed: Boolean)
}