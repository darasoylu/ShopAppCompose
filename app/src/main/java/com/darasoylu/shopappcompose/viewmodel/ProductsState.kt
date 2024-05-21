package com.darasoylu.shopappcompose.viewmodel

import com.darasoylu.shopappcompose.data.model.ProductModel

data class ProductsState(
    val loading: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val error: Throwable? = null
)