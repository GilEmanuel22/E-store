package com.example.e_store.data.remote_DB

import androidx.room.Insert
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val productService:ProductService
) :BaseDataSource(){
    suspend fun getProducts() =getResult { productService.getAllProducts() }
    suspend fun getProduct(id:Int)= getResult { productService.getProductById(id) }
    suspend fun getCategory(category:String)=getResult { productService.getCategoryProducts(category) }
}