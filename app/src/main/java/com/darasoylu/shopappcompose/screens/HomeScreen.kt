package com.darasoylu.shopappcompose.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.darasoylu.shopappcompose.R
import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.navigation.Routes
import com.darasoylu.shopappcompose.screens.components.Loader
import com.darasoylu.shopappcompose.ui.theme.GreyCardText
import com.darasoylu.shopappcompose.util.LoadingAnimation
import com.darasoylu.shopappcompose.util.formatPrice
import com.darasoylu.shopappcompose.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var selectedCategoryPosition by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf(CategoryModel(name = "All")) }
    val productState = homeViewModel.productsState
    val categoryState = homeViewModel.categoriesState

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (productState.loading || categoryState.loading) {
                Loader()
            }
            if (categoryState.categories.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    itemsIndexed(categoryState.categories) { index, item ->
                        Card(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .aspectRatio(2f)
                                .clickable {
                                    selectedCategory = item
                                    selectedCategoryPosition = index
                                },
                            border = BorderStroke(1.dp, Color.Black),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedCategoryPosition == index) Color.Black else Color.White,
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    fontSize = 16.sp,
                                    color = if (selectedCategoryPosition == index) Color.White else GreyCardText,
                                    text = item.name ?: "",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            if (productState.products.isNotEmpty()) {
                val categoryFilteredProductList =
                    productState.products.filter { it.categoryId == selectedCategory.categoryId }
                val products =
                    if (selectedCategory.name == "All") productState.products else categoryFilteredProductList

                LazyVerticalGrid(
                    modifier = Modifier.background(Color.White),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(40.dp),
                    columns = GridCells.Fixed(2)
                ) {
                    itemsIndexed(products) { index, item ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(1.dp),
                        ) {
                            Card(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clickable {
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            key = "product",
                                            value = item
                                        )
                                        navController.navigate(Routes.PRODUCT_DETAIL_SCREEN)
                                    }
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    SubcomposeAsyncImage(
                                        model = item.image,
                                        contentDescription = null,
                                        loading = { LoadingAnimation() },
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp)
                                            .size(32.dp)
                                    )
                                }
                            }
                            Text(
                                text = item.name!!,
                                color = Color.Black,
                                maxLines = 1,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = stringResource(
                                    R.string.product_price,
                                    formatPrice(item.price!!)
                                ),
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}
