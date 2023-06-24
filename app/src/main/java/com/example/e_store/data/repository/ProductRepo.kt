package com.example.e_store.data.repository


import com.example.e_store.data.local_DB.ProductDao
import com.example.e_store.data.remote_DB.ProductRemoteDataSource
import com.example.e_store.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepo @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductDao
) {

    fun getProducts()= performFetchingAndSaving(
        {localDataSource.getAllProducts()},
        {remoteDataSource.getProducts()},
        {localDataSource.insertAllProducts(it)}
    )

    fun getProduct(id:Int)= performFetchingAndSaving(
        {localDataSource.getProduct(id)},
        {remoteDataSource.getProduct(id)},
        {localDataSource.insertProduct(it)}
    )
}