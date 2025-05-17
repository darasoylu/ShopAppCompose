package com.darasoylu.shopappcompose.di

import com.darasoylu.shopappcompose.domain.repository.ShopAppRepository
import com.darasoylu.shopappcompose.domain.usecase.GetCategoriesUseCase
import com.darasoylu.shopappcompose.domain.usecase.GetProductsUseCase
import com.darasoylu.shopappcompose.domain.usecase.cart.AddToCartUseCase
import com.darasoylu.shopappcompose.domain.usecase.cart.CartUseCases
import com.darasoylu.shopappcompose.domain.usecase.cart.ClearCartUseCase
import com.darasoylu.shopappcompose.domain.usecase.cart.RemoveFromCartUseCase
import com.darasoylu.shopappcompose.domain.usecase.remote_config.FetchRemoteConfigUseCase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: ShopAppRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(repository: ShopAppRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddToCartUseCase(): AddToCartUseCase {
        return AddToCartUseCase()
    }

    @Provides
    @Singleton
    fun provideRemoveFromCartUseCase(): RemoveFromCartUseCase {
        return RemoveFromCartUseCase()
    }

    @Provides
    @Singleton
    fun provideClearCartUseCase(): ClearCartUseCase {
        return ClearCartUseCase()
    }

    @Provides
    @Singleton
    fun provideFetchRemoteConfigUseCase(remoteConfig: FirebaseRemoteConfig): FetchRemoteConfigUseCase {
        return FetchRemoteConfigUseCase(remoteConfig)
    }

    @Provides
    @Singleton
    fun provideCartUseCases(
        addToCartUseCase: AddToCartUseCase,
        removeFromCartUseCase: RemoveFromCartUseCase,
        clearCartUseCase: ClearCartUseCase
    ): CartUseCases {
        return CartUseCases(
            addToCart = addToCartUseCase,
            removeFromCart = removeFromCartUseCase,
            clearCart = clearCartUseCase
        )
    }
} 