package com.example.e_store.ui.all_products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_store.data.repository.CartRepo
import com.example.e_store.data.repository.ProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductsViewModel @Inject constructor(
    productRepo:ProductRepo,
    private val cartRepo: CartRepo
):ViewModel() {
    val products = productRepo.getProducts()

    fun deleteAll(){
        viewModelScope.launch {
            cartRepo.deleteAll()
        }
    }

}