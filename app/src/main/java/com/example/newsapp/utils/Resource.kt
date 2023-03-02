package com.example.newsapp.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Sucess<T>(data: T) : Resource<T>(data)
    class Failure<T>(data: T? = null, message: String?) : Resource<T>(data, message)
    class Loading<T>() : Resource<T>()
}