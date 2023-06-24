package com.example.e_store.di

import android.content.Context
import okhttp3.OkHttpClient
import com.example.e_store.data.local_DB.AppDatabase
import com.example.e_store.data.remote_DB.ProductService
import com.example.fullarchi.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson):Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    fun provideGson():Gson =GsonBuilder().create()

    @Provides
    fun provideProductService(retrofit: Retrofit):ProductService= retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext:Context):AppDatabase= AppDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase)= database.productDao()

    @Provides
    @Singleton
    fun provideCartItemDao(database: AppDatabase)= database.cartItemDao()





}