package com.darasoylu.shopappcompose.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.darasoylu.shopappcompose.data.model.ProductModel

@Composable
fun ProductDetailScreen(
    navController: NavController,
    product: ProductModel
) {
    Column {
        Text(text = "Product Detail")
       // Log.i("asmdasd", product.toString())
    }
}