package com.example.e_store.data.local_DB

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_store.data.models.Product

@androidx.room.Dao
interface ProductDao {

    @Query("select * from product")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("select * from product where id = :id")
    fun getProduct(id:Int): LiveData<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<Product>)
}