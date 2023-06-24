package com.example.tiuta.utils

import com.example.e_store.utils.FirebaseResource

inline fun <T> safeCall(action: () -> FirebaseResource<T>): FirebaseResource<T> {
    return try {
        action()
    } catch (e: Exception) {
        FirebaseResource.Error(e.message ?: "An unknown Error Occurred")
    }
}