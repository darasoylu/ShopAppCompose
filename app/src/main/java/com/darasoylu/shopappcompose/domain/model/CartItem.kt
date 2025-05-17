package com.darasoylu.shopappcompose.domain.model

import com.darasoylu.shopappcompose.data.model.ProductModel

data class CartItem(
    val product: ProductModel,
    val quantity: Int,
    val totalPrice: Double
)