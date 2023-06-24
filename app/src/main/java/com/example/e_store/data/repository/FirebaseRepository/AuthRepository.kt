package com.example.tiuta.Repository

import com.example.e_store.utils.FirebaseResource
import com.example.tiuta.User


interface AuthRepository {
    suspend fun currentUser(): FirebaseResource<User>
    suspend fun login(email:String,password:String): FirebaseResource<User>
    suspend fun createUser(userName: String,email: String,password: String,phoneNumber:String) : FirebaseResource<User>
    fun logout()
}