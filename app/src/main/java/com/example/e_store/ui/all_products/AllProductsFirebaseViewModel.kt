package com.example.e_store.ui.all_products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_store.data.models.Product
import com.example.e_store.utils.FirebaseResource
import com.example.tiuta.Repository.AuthRepository
import com.example.tiuta.Repository.ProductRepository
import kotlinx.coroutines.launch

class AllProductsFirebaseViewModel(private val authRep: AuthRepository
                                   , productRep: ProductRepository
):
    ViewModel() {
    val _productsStatus : MutableLiveData<FirebaseResource<List<Product>>> = MutableLiveData()


    init{
        productRep.getProductLiveData(_productsStatus)
    }

    fun signOut(){
        authRep.logout()
    }
    class ProductViewModelFactory(val authRepo: AuthRepository,val productRepo:ProductRepository):
        ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllProductsFirebaseViewModel(authRepo,productRepo) as T
        }
    }
}