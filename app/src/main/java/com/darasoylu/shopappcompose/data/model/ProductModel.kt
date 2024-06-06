package com.darasoylu.shopappcompose.data.model

data class ProductModel(
    val productId: String?= null,
    val name: String?= null,
    val image: String?= null,
    val price: Long?= null,
    val categoryId: String?= null
)