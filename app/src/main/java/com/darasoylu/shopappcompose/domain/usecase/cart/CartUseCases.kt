package com.darasoylu.shopappcompose.domain.usecase.cart

import javax.inject.Inject

data class CartUseCases @Inject constructor(
    val addToCart: AddToCartUseCase,
    val removeFromCart: RemoveFromCartUseCase,
    val clearCart: ClearCartUseCase
) 