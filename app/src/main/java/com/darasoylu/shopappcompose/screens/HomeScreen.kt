package com.darasoylu.shopappcompose.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.screens.components.Loader
import com.darasoylu.shopappcompose.ui.theme.GreyCardRadius
import com.darasoylu.shopappcompose.ui.theme.GreyCardText
import com.darasoylu.shopappcompose.util.Resource
import com.darasoylu.shopappcompose.viewmodel.HomeViewModel
import com.darasoylu.shopappcompose.viewmodel.ProductsState

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Log.i("askjdasd11", "here")

    //val news = homeViewModel.news.collectAsState()

    val productState = homeViewModel.productsState
    val categoryState = homeViewModel.categoriesState
    var selectedPosition by remember { mutableIntStateOf(0) }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = "Daraaaaa")
            Log.i("askjdasd1", "here")


            //HorizontalBanner(Image)
            //HorizontalList(Icon?,Name)
            //VerticalGridList
            //->CardItem(Image,Name,Comment)

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
                    selectedPosition = selectedPosition,
                    onCategorySelected = {
                        selectedPosition = it
                    }
                )
            }


            if (productState.products.isNotEmpty()) {
                Log.i("askjdasd2", productState.products.toString())
                Text(text = "Testtttttt")
            }

        }

    }
}

@Composable
fun CategorySection(categories: List<CategoryModel>, selectedPosition: Int, onCategorySelected: (Int) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryCard(
                category = item,
                isSelected = selectedPosition == index,
                onClick = {
                    onCategorySelected(index)
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
            .height(70.dp)
            .fillMaxWidth()
            .aspectRatio(1.5f),
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
                fontSize = 18.sp,
                color = if (isSelected) Color.White else GreyCardText,
                text = category.name ?: "",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}