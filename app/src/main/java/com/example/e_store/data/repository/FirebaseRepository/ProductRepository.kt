package com.example.tiuta.Repository

import androidx.lifecycle.MutableLiveData
import com.example.e_store.data.models.Product
import com.example.e_store.utils.FirebaseResource
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    suspend fun addProduct(title: String,price:String,category:String,description:String,image:String):
            FirebaseResource<Void>
    suspend fun deleteProduct(id:String) :FirebaseResource<Void>
    suspend fun getProduct(id: String) :FirebaseResource<Product>

    suspend fun getProducts() :FirebaseResource<List<Product>>
    fun getProductFlow(): Flow<FirebaseResource<List<Product>>>
    fun getProductLiveData(data: MutableLiveData<FirebaseResource<List<Product>>>)
}