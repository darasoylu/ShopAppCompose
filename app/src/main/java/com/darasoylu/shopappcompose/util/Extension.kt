package com.darasoylu.shopappcompose.util

fun formatPrice(number: Double): String {
    return if (number % 1.0 == 0.0) {
        number.toInt().toString()
    } else {
        number.toString()
    }
}