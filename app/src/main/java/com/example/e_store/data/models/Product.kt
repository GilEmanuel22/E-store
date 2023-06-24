package com.example.e_store.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    val id:Int,
    val title:String,
    val price:String,
    val category: String,
    val description:String,
    val image:String
) {

}