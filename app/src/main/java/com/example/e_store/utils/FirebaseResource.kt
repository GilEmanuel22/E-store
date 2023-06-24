package com.example.e_store.utils

open class FirebaseResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : FirebaseResource<T>(data)
    class Loading<T>(data: T? = null) : FirebaseResource<T>(data)
    class Error<T>(message: String, data: T? = null) : FirebaseResource<T>(data, message)
}