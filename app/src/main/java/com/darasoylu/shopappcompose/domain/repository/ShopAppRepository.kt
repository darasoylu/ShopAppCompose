package com.darasoylu.shopappcompose.domain.repository

import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.util.Resource
import kotlinx.coroutines.flow.Flow

interface ShopAppRepository {

    fun getProducts() : Flow<Resource<List<ProductModel>>>

    fun getCategories() : Flow<Resource<List<CategoryModel>>>

}