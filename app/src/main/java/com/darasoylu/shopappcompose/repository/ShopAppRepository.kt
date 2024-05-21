package com.darasoylu.shopappcompose.repository

import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.util.Resource
import kotlinx.coroutines.flow.Flow

interface ShopAppRepository {

    fun getProducts(collectionPath: String) : Flow<Resource<List<ProductModel>>>

    fun getCategories(collectionPath: String) : Flow<Resource<List<CategoryModel>>>

}