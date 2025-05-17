package com.darasoylu.shopappcompose.di

import com.darasoylu.shopappcompose.data.repository.ShopAppRepositoryImpl
import com.darasoylu.shopappcompose.domain.repository.ShopAppRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideShopAppRepository(
        firebaseFirestore: FirebaseFirestore
    ): ShopAppRepository {
        return ShopAppRepositoryImpl(firebaseFirestore)
    }
} 