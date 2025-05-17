package com.darasoylu.shopappcompose.domain.usecase.cart

import com.darasoylu.shopappcompose.domain.model.CartItem
import javax.inject.Inject

class ClearCartUseCase @Inject constructor() {
    
    operator fun invoke(): Pair<List<CartItem>, Double> {
        return Pair(emptyList(), 0.0)
    }
} 