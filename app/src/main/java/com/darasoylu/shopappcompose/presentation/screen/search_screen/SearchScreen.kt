package com.darasoylu.shopappcompose.presentation.screen.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.darasoylu.shopappcompose.R
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.navigation.Routes
import com.darasoylu.shopappcompose.presentation.components.LoadingAnimation
import com.darasoylu.shopappcompose.presentation.theme.BackgroundGreen
import com.darasoylu.shopappcompose.presentation.theme.DarkGreen
import com.darasoylu.shopappcompose.presentation.theme.PrimaryGreen
import com.darasoylu.shopappcompose.presentation.viewmodel.HomeViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    val recentSearches = remember {
        listOf("Book", "Milk", "T-shirt", "Sneaker", "Watch")
    }

    val popularCategories = remember {
        listOf("Apple", "Banana", "Orange", "Mango")
    }
    
    val productsState = homeViewModel.productsState
    
    val filteredProducts = remember(searchText, productsState.products) {
        if (searchText.isBlank()) {
            emptyList()
        } else {
            productsState.products.filter {
                it.name?.contains(searchText, ignoreCase = true) == true
            }
        }
    }
    
    val isSearching = searchText.isNotBlank()
    
    LaunchedEffect(Unit) {
        homeViewModel.reloadProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SearchBar(
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            onSearch = {  }
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        if (isSearching) {
            Text(
                text = "Search Results",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            when {
                productsState.loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryGreen)
                    }
                }
                
                productsState.error != null -> {
                    Text(
                        text = "Error: ${productsState.error?.message ?: "Unknown error"}",
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
                
                filteredProducts.isEmpty() -> {
                    Text(
                        text = "No products found for '$searchText'",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
                
                else -> {
                    filteredProducts.forEach { product ->
                        ProductSearchResult(
                            product = product,
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "product",
                                    value = product
                                )
                                navController.navigate(Routes.PRODUCT_DETAIL_SCREEN)
                            }
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        } else {

            Text(
                text = "Recent Searches",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            recentSearches.forEach { searchItem ->
                RecentSearchItem(
                    searchText = searchItem,
                    onSearchClick = { searchText = searchItem },
                    onDeleteClick = { }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Popular Searches",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            FlowRowCategories(
                categories = popularCategories,
                onCategoryClick = { searchText = it }
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ProductSearchResult(
    product: ProductModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = product.image,
                    contentDescription = product.name,
                    loading = { LoadingAnimation() },
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(50.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name ?: "",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "₺${product.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = DarkGreen
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color.LightGray.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            TextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                placeholder = {
                    Text(
                        text = "Search product",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(
                            onClick = { onSearchTextChange("") },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear search",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun RecentSearchItem(
    searchText: String,
    onSearchClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSearchClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.ic_history),
                contentDescription = "History",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = searchText,
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun FlowRowCategories(
    categories: List<String>,
    onCategoryClick: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.size) { index ->
            CategoryChip(
                category = categories[index],
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: String,
    onCategoryClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onCategoryClick(category) }
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryGreen
        )
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.White,
            fontSize = 14.sp
        )
    }
}