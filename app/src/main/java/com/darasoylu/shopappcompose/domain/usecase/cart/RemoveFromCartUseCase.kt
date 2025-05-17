package com.darasoylu.shopappcompose.domain.usecase.cart

import com.darasoylu.shopappcompose.domain.model.CartItem
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor() {
    
    operator fun invoke(
        productId: String,
        currentCartItems: List<CartItem>
    ): Pair<List<CartItem>, Double> {
        val mutableCartItems = currentCartItems.toMutableList()
        
        val index = mutableCartItems.indexOfFirst { it.product.productId == productId }
        if (index != -1) {
            val item = mutableCartItems[index]
            if (item.quantity > 1) {
                val newQuantity = item.quantity - 1
                val newTotalPrice = (item.product.price ?: 0.0) * newQuantity

                mutableCartItems[index] = item.copy(
                    quantity = newQuantity,
                    totalPrice = newTotalPrice
                )
            } else {
                mutableCartItems.removeAt(index)
            }
        }

        return Pair(mutableCartItems, calculateCartTotal(mutableCartItems))
    }
    
    private fun calculateCartTotal(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { it.totalPrice }
    }
} 