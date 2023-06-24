package com.example.e_store.data.repository

import android.app.Application
import com.example.e_store.data.local_DB.AppDatabase
import com.example.e_store.data.local_DB.CartItemDao
import com.example.e_store.data.models.CartItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepo @Inject constructor(private val cartItemDao: CartItemDao) {


    fun getItems()=cartItemDao?.getItems()
    suspend fun addItem(item:CartItem) = cartItemDao?.addItem(item)
    suspend fun deleteItem(item: CartItem)=cartItemDao?.deleteItem(item)
    fun getItem(id:Int)=cartItemDao?.getItem(id)
    suspend fun deleteAll() {
        cartItemDao?.deleteAll()
    }
}