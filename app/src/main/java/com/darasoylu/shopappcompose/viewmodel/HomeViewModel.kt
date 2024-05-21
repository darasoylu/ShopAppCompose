package com.darasoylu.shopappcompose.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darasoylu.shopappcompose.repository.ShopAppRepositoryImpl
import com.darasoylu.shopappcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ShopAppRepositoryImpl
) : ViewModel() {

//    private val _news: MutableStateFlow<ProductsState> = MutableStateFlow(ProductsState(loading = true))
  //  var news: StateFlow<ProductsState> = _news

    var productsState by mutableStateOf(ProductsState())
    var categoriesState by mutableStateOf(CategoryState())


    init {
        getProducts("products")
        getCategories("categories")
    }

    private fun getProducts(collectionPath: String) {
        viewModelScope.launch {
            //(Dispatchers.IO)
            repository.getProducts(collectionPath).collect {
                productsState = when (it) {
                    is Resource.Error -> {
                        Log.i("alnksdasd5", "here")
                        productsState.copy(
                            loading = false,
                            error = it.exception
                        )
                    }
                    is Resource.Loading -> {
                        Log.i("alnksdasd3", "here")
                        productsState.copy(
                            loading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.i("alnksdasd4", it.data.toString())
                        productsState.copy(
                            loading = false,
                            products = it.data
                        )
                    }
                }
            }
        }
    }

    private fun getCategories(collectionPath: String) {
        viewModelScope.launch {
            //(Dispatchers.IO)
            repository.getCategories(collectionPath).collect {
                categoriesState = when (it) {
                    is Resource.Error -> {
                        Log.i("alnksdasd7", "here")
                        categoriesState.copy(
                            loading = false,
                            error = it.exception
                        )
                    }
                    is Resource.Loading -> {
                        Log.i("alnksdasd8", "here")
                        categoriesState.copy(
                            loading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.i("alnksdasd9", it.toString())
                        categoriesState.copy(
                            loading = false,
                            categories = it.data.sortedBy { it.sort }
                        )
                    }
                }
            }
        }
    }

}