package com.darasoylu.shopappcompose.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.domain.usecase.GetCategoriesUseCase
import com.darasoylu.shopappcompose.domain.usecase.GetProductsUseCase
import com.darasoylu.shopappcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    var productsState by mutableStateOf(ProductsState())
    var categoriesState by mutableStateOf(CategoryState())

    init {
        getProducts()
        getCategories()
    }

    fun reloadProducts() {
        getProducts()
    }

    fun reloadCategories() {
        getCategories()
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect {
                productsState = when (it) {
                    is Resource.Error -> {
                        productsState.copy(
                            loading = false,
                            error = it.exception
                        )
                    }

                    is Resource.Loading -> {
                        productsState.copy(
                            loading = true
                        )
                    }

                    is Resource.Success -> {
                        productsState.copy(
                            loading = false,
                            products = it.data
                        )
                    }
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect {
                categoriesState = when (it) {
                    is Resource.Error -> {
                        categoriesState.copy(
                            loading = false,
                            error = it.exception
                        )
                    }

                    is Resource.Loading -> {
                        categoriesState.copy(
                            loading = true
                        )
                    }

                    is Resource.Success -> {
                        categoriesState.copy(
                            loading = false,
                            categories = it.data
                        )
                    }
                }
            }
        }
    }

}

data class CategoryState(
    val loading: Boolean = false,
    val categories: List<CategoryModel> = emptyList(),
    val error: Throwable? = null
)

data class ProductsState(
    val loading: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val error: Throwable? = null
)