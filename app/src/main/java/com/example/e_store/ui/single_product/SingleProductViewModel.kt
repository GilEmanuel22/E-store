package com.example.e_store.ui.single_product

import android.view.animation.Transformation
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.e_store.data.models.CartItem
import com.example.e_store.data.repository.CartRepo
import com.example.e_store.data.repository.ProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleProductViewModel @Inject constructor(
    private val productRepo: ProductRepo,
    private val cartRepo: CartRepo
):ViewModel() {
    private val  _id=MutableLiveData<Int>()
    fun setId(id:Int){
        _id.value=id
    }

    fun deleteAll(){
        viewModelScope.launch {
            cartRepo.deleteAll()
        }
    }
    val product = _id.switchMap {
        productRepo.getProduct(it)
    }
}