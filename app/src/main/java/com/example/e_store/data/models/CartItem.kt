package com.example.e_store.data.models

import android.os.Parcelable
import androidx.appcompat.widget.AppCompatImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ShoppingCart")
data class CartItem(
    @PrimaryKey
    val id:Int,
    val title:String,
    val price:String,
    val category: String,
    val description:String,
    val image: String,
    val quantity:Int):Parcelable {
}