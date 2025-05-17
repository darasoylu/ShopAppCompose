package com.darasoylu.shopappcompose.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.domain.model.CartItem
import com.darasoylu.shopappcompose.domain.model.PromotionData
import com.darasoylu.shopappcompose.domain.usecase.cart.CartUseCases
import com.darasoylu.shopappcompose.domain.usecase.remote_config.FetchRemoteConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val cartUseCases: CartUseCases,
    private val fetchRemoteConfigUseCase: FetchRemoteConfigUseCase
) : ViewModel() {

    var isFeatureEnabled by mutableStateOf(false)
        private set
        
    var promotionData by mutableStateOf<PromotionData?>(null)
        private set

    // Total price of the cart
    var cartTotal by mutableStateOf(0.0)
        private set

    // List of cart items
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    init {
        Log.d("SharedViewModel2", cartItems.toString())
        Log.d("SharedViewModel3", cartTotal.toString())
    }

    fun fetchRemoteConfigs() {
        viewModelScope.launch {
            val (featureEnabled, promotion) = fetchRemoteConfigUseCase()
            isFeatureEnabled = featureEnabled
            promotionData = promotion
            
            Log.d("SharedViewModel", "Feature enabled: $isFeatureEnabled")
            Log.d("SharedViewModel", "Promotion data: $promotionData")
        }
    }

    fun addToCart(product: ProductModel) {
        val result = cartUseCases.addToCart(product, _cartItems.toList())
        updateCartState(result)
    }

    fun removeFromCart(productId: String) {
        val result = cartUseCases.removeFromCart(productId, _cartItems.toList())
        updateCartState(result)
    }

    fun clearCart() {
        val result = cartUseCases.clearCart()
        updateCartState(result)
    }

    private fun updateCartState(result: Pair<List<CartItem>, Double>) {
        val (newItems, newTotal) = result
        
        _cartItems.clear()
        _cartItems.addAll(newItems)
        cartTotal = newTotal
    }
}