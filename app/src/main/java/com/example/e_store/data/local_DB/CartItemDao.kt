package com.example.e_store.data.local_DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.e_store.data.models.CartItem

@Dao
interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item:CartItem)


    @Delete
    suspend fun deleteItem(vararg items:CartItem)

    @Update
    suspend fun updateItem(item:CartItem)

    @Query("select * from ShoppingCart")
    fun getItems(): LiveData<List<CartItem>>

    @Query("SELECT * FROM ShoppingCart WHERE id == :id")
    fun getItem(id:Int):CartItem

    @Query("delete from ShoppingCart")
    suspend fun deleteAll()

}