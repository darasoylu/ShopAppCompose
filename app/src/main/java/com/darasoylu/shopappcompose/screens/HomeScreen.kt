package com.darasoylu.shopappcompose.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.darasoylu.shopappcompose.R
import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.screens.components.Loader
import com.darasoylu.shopappcompose.ui.theme.GreyCardText
import com.darasoylu.shopappcompose.util.formatPrice
import com.darasoylu.shopappcompose.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Log.i("askjdasd11", "here")

    //val news = homeViewModel.news.collectAsState()

    val productState = homeViewModel.productsState
    val categoryState = homeViewModel.categoriesState
    var selectedCategoryPosition by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf(CategoryModel(name = "All")) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            //Banner Screen
            /**
            Image(
                imageVector = Icons.Outlined.FavoriteBorder,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )

            Image(
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth(),
                painter = "",
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )
            **/

            Log.i("askjdasd1", "here")

            if (productState.loading) {
                Log.i("askjdasd3", "here")
                Loader()
            }

            if (categoryState.loading) {
                Log.i("askjdasd4", "here")
                Loader()
            }

            if (categoryState.categories.isNotEmpty()) {
                Log.i("askjdasd5", categoryState.categories.toString())
                //Text(text = "Categories")
                CategorySection(
                    categories = categoryState.categories,
                    selectedPosition = selectedCategoryPosition,
                    onCategorySelected = { categoryId, selectedPosition  ->
                        selectedCategory = categoryId!!
                        selectedCategoryPosition = selectedPosition
                    }
                )
            }

            Spacer(modifier = Modifier.height(5.dp))


            if (productState.products.isNotEmpty()) {
                val categoryFilteredProductList = productState.products.filter { it.categoryId == selectedCategory.categoryId }
                val products = if (selectedCategory.name == "All") productState.products else categoryFilteredProductList

                ProductSection(products = products,
                    selectedPosition = selectedCategoryPosition,
                    onCategorySelected = {
                        selectedCategoryPosition = it
                    }
                )
            }

        }

    }
}

@Composable
fun CategorySection(categories: List<CategoryModel>, selectedPosition: Int, onCategorySelected: (CategoryModel?, Int) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryCard(
                category = item,
                isSelected = selectedPosition == index,
                onClick = {
                    Log.i("lnjadas", item.categoryId.toString())
                    onCategorySelected(item, index)
                }
            )
        }
    }
}

@Composable
fun CategoryCard(
    category: CategoryModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .aspectRatio(2f),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Black else Color.White,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontSize = 16.sp,
                color = if (isSelected) Color.White else GreyCardText,
                text = category.name ?: "",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ProductSection(products: List<ProductModel>, selectedPosition: Int, onCategorySelected: (Int) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.background(Color.White),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        columns = GridCells.Fixed(2)
    ) {
        itemsIndexed(products) { index, item ->
            ProductCard(
                product = item,
                isSelected = selectedPosition == index,
                onClick = {
                    Log.i("lnasdasd", "here")
                }
            )
        }
    }
}

@Composable
fun ProductCard(
    product: ProductModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        Card(
            modifier = Modifier
                .aspectRatio(1f)
        ) {
            Box (
                modifier = Modifier
                    .clickable { onClick() }
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
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
            text = product.name!!,
            color = Color.Black,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = stringResource(R.string.product_price, formatPrice(product.price!!)),
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}