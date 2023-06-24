package com.example.e_store.data.remote_DB

import com.example.e_store.data.models.AllProducts
import com.example.e_store.data.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/{category}")
    suspend fun getCategoryProducts(@Path("category") category: String): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<Product>
}