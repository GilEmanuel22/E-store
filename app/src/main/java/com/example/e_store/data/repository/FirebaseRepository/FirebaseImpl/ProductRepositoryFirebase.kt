package com.example.tiuta.Repository.FirebaseImpl

import androidx.lifecycle.MutableLiveData
import com.example.e_store.data.models.Product
import com.example.e_store.utils.FirebaseResource
import com.example.tiuta.Repository.ProductRepository
import com.example.tiuta.utils.safeCall
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ProductRepositoryFirebase: ProductRepository {

    private val productRef = FirebaseFirestore.getInstance().collection("products")
    override suspend fun addProduct(title: String
                                    ,price:String
                                    ,category: String
                                    ,description:String
                                    ,image:String)
            = withContext(Dispatchers.IO){
        safeCall {
            val productID = productRef.document().id
            val product = Product(productID.toInt(),title,price,category,description,image)
            val addition = productRef.document(productID).set(product).await()
            FirebaseResource.Success(addition)
        }
    }

    override suspend fun deleteProduct(chosenID: String) = withContext(Dispatchers.IO){
        safeCall {
            val result = productRef.document(chosenID).delete().await()
            FirebaseResource.Success(result)
        }
    }



    override suspend fun getProduct(id: String) = withContext(Dispatchers.IO){
        safeCall {
            val result = productRef.document(id).get().await()
            val product = result.toObject(Product::class.java)
            FirebaseResource.Success(product!!)
        }
    }

    override suspend fun getProducts() = withContext(Dispatchers.IO){
        safeCall {
            val result = productRef.get().await()
            val products = result.toObjects(Product::class.java)
            FirebaseResource.Success(products)
        }
    }

    override fun getProductFlow(): Flow<FirebaseResource<List<Product>>> {
        TODO("Not yet implemented")
    }

    override fun getProductLiveData(data: MutableLiveData<FirebaseResource<List<Product>>>) {

        data.postValue(FirebaseResource.Loading())
        productRef.orderBy("titles").addSnapshotListener{snepshot,e->
            if(e!= null)
            {
                data.postValue(FirebaseResource.Error(e.localizedMessage!!))
            }
            if(snepshot!= null && !snepshot.isEmpty())
            {
                data.postValue(FirebaseResource.Success(snepshot.toObjects(Product::class.java)))
            }

        }
    }
}