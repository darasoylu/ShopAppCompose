package com.darasoylu.shopappcompose.viewmodel

import com.darasoylu.shopappcompose.data.model.CategoryModel

data class CategoryState(
    val loading: Boolean = false,
    val categories: List<CategoryModel> = emptyList(),
    val error: Throwable? = null
)