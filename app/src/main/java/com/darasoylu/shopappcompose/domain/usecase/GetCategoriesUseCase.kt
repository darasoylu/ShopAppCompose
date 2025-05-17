package com.darasoylu.shopappcompose.domain.usecase

import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.domain.repository.ShopAppRepository
import com.darasoylu.shopappcompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: ShopAppRepository
) {
    operator fun invoke(): Flow<Resource<List<CategoryModel>>> {
        return repository.getCategories().map { resource ->
            when (resource) {
                is Resource.Success -> {
                    Resource.Success(resource.data.sortedBy { it.sort })
                }
                is Resource.Error -> resource
                is Resource.Loading -> resource
            }
        }
    }
} 