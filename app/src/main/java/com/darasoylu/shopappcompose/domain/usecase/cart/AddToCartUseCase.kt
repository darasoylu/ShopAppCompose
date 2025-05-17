package com.darasoylu.shopappcompose.domain.usecase.cart

import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.domain.model.CartItem
import javax.inject.Inject

class AddToCartUseCase @Inject constructor() {
    
    operator fun invoke(
        product: ProductModel,
        currentCartItems: List<CartItem>
    ): Pair<List<CartItem>, Double> {
        val price = product.price ?: return Pair(currentCartItems, calculateCartTotal(currentCartItems))
        val mutableCartItems = currentCartItems.toMutableList()
        
        val existingItemIndex = mutableCartItems.indexOfFirst { it.product.productId == product.productId }

        if (existingItemIndex != -1) {
            val existingItem = mutableCartItems[existingItemIndex]
            val newQuantity = existingItem.quantity + 1
            val newTotalPrice = price * newQuantity

            mutableCartItems[existingItemIndex] = existingItem.copy(
                quantity = newQuantity,
                totalPrice = newTotalPrice
            )
        } else {
            mutableCartItems.add(
                CartItem(
                    product = product,
                    quantity = 1,
                    totalPrice = price
                )
            )
        }

        return Pair(mutableCartItems, calculateCartTotal(mutableCartItems))
    }
    
    private fun calculateCartTotal(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { it.totalPrice }
    }
} 