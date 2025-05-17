package com.darasoylu.shopappcompose.domain.usecase

import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.domain.repository.ShopAppRepository
import com.darasoylu.shopappcompose.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ShopAppRepository
) {
    operator fun invoke(): Flow<Resource<List<ProductModel>>> {
        return repository.getProducts()
    }
} 