package com.darasoylu.shopappcompose.domain.model

data class PromotionData(
    val title: String = "",
    val description: String = "",
    val discountAmount: String = "",
    val couponCode: String = "",
    val validityText: String = ""
)