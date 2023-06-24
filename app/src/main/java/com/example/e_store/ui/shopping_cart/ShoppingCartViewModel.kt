package com.example.e_store.ui.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_store.data.models.CartItem
import com.example.e_store.data.repository.CartRepo

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(private val cartRepo: CartRepo):ViewModel(){
    val items= cartRepo.getItems()

    fun addItem(item: CartItem){
        viewModelScope.launch {
            cartRepo.addItem(item)
        }
    }

    fun deleteItem(item:CartItem){
        viewModelScope.launch {

            cartRepo.deleteItem(item)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            cartRepo.deleteAll()
        }
    }
}