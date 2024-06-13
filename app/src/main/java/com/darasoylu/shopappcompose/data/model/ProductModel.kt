package com.darasoylu.shopappcompose.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val productId: String?= null,
    val name: String?= null,
    val image: String?= null,
    val price: Double?= null,
    val categoryId: String?= null
): Parcelable