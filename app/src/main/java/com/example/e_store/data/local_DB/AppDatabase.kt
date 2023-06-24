package com.example.e_store.data.local_DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.e_store.data.models.CartItem
import com.example.e_store.data.models.Product

@Database(entities = [CartItem::class,Product::class], version = 2, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartItemDao():CartItemDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "products_db"
                )
                    .fallbackToDestructiveMigration().build()
                    .also {
                        instance = it
                    }
            }

        }
    }
}