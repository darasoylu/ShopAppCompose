package com.darasoylu.shopappcompose.presentation.screen.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.navigation.Routes
import com.darasoylu.shopappcompose.presentation.components.Loader
import com.darasoylu.shopappcompose.presentation.components.LoadingAnimation
import com.darasoylu.shopappcompose.presentation.theme.BackgroundGreen
import com.darasoylu.shopappcompose.presentation.theme.DarkGreen
import com.darasoylu.shopappcompose.presentation.theme.GreenText
import com.darasoylu.shopappcompose.presentation.theme.GreyBorder
import com.darasoylu.shopappcompose.presentation.theme.LightGreen
import com.darasoylu.shopappcompose.presentation.theme.LightGreyGreen
import com.darasoylu.shopappcompose.presentation.theme.PrimaryGreen
import com.darasoylu.shopappcompose.presentation.viewmodel.HomeViewModel
import com.darasoylu.shopappcompose.presentation.viewmodel.SharedViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    var selectedCategoryPosition by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf(CategoryModel(name = "All")) }
    val productState = homeViewModel.productsState
    val categoryState = homeViewModel.categoriesState

    val cartTotal = sharedViewModel.cartTotal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
    ) {
        // Welcome section
        WelcomeRow(
            cartTotal = cartTotal,
            onCartClick = {
                navController.navigate(Routes.CARD_SCREEN)
            }
        )

        // Categories header
        CategoriesHeader()

        // Categories section with its own loading and error handling
        when {
            categoryState.loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryGreen)
                }
            }

            categoryState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Error",
                            tint = Color.Red
                        )
                        Text(
                            text = "Categories error. Tap to retry.",
                            color = Color.Red
                        )
                        IconButton(onClick = { homeViewModel.reloadCategories() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Retry",
                                tint = PrimaryGreen
                            )
                        }
                    }
                }
            }

            else -> {
                // Categories
                CategoriesSection(
                    categories = categoryState.categories,
                    selectedCategoryPosition = selectedCategoryPosition,
                    onCategorySelected = { category, position ->
                        selectedCategory = category
                        selectedCategoryPosition = position
                    }
                )
            }
        }

        // Products header
        ProductsHeader()

        // Products section with its own loading and error handling
        when {
            productState.loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Loader()
                }
            }

            productState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorView(
                        errorMessage = productState.error?.message
                            ?: "An error occurred loading products",
                        onRetry = {
                            homeViewModel.reloadProducts()
                        }
                    )
                }
            }

            else -> {
                // Products grid - only this part is scrollable
                val filteredProducts = if (selectedCategory.name == "All") {
                    productState.products
                } else {
                    productState.products.filter { it.categoryId == selectedCategory.categoryId }
                }

                if (filteredProducts.isNotEmpty()) {
                    // ProductsGrid takes remaining space
                    Box(modifier = Modifier.weight(1f)) {
                        ProductsGrid(
                            products = filteredProducts,
                            navController = navController,
                            onAddToCart = { product ->
                                sharedViewModel.addToCart(product)
                            }
                        )
                    }
                } else {
                    // No products found message
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No products found in this category",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ErrorView(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Error",
            tint = Color.Red,
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen
            )
        ) {
            Text("Try Again")
        }
    }
}

@Composable
fun WelcomeRow(
    cartTotal: Double = 0.0,
    onCartClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryGreen)
            .padding(vertical = 24.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Hello, Jack",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "What would you like to buy today?",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        // Cart icon with total price
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            modifier = Modifier
                .height(40.dp)
                .clickable { onCartClick() }
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Divider(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(Color.LightGray.copy(alpha = 0.5f))
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "₺${String.format("%.2f", cartTotal)}",
                    color = Color(0xFF483285),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CategoriesSection(
    categories: List<CategoryModel>,
    selectedCategoryPosition: Int,
    onCategorySelected: (CategoryModel, Int) -> Unit
) {
    if (categories.isNotEmpty()) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(categories) { index, item ->
                Card(
                    modifier = Modifier
                        .height(40.dp)
                        .wrapContentWidth()
                        .clickable {
                            onCategorySelected(item, index)
                        },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedCategoryPosition == index) PrimaryGreen else LightGreyGreen,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (selectedCategoryPosition == index) 4.dp else 0.dp
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            fontSize = 14.sp,
                            color = if (selectedCategoryPosition == index) Color.White else GreenText,
                            text = item.name ?: "",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsHeader() {
    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = "Products",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = GreenText,
        modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
    )
}

@Composable
fun CategoriesHeader() {
    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = "Categories",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = GreenText,
        modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
    )
}

@Composable
fun ProductsGrid(
    products: List<ProductModel>,
    navController: NavController,
    onAddToCart: (ProductModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                navController = navController,
                onAddToCart = onAddToCart
            )
        }
    }
}

@Composable
fun ProductCard(
    product: ProductModel,
    navController: NavController,
    onAddToCart: (ProductModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "product",
                    value = product
                )
                navController.navigate(Routes.PRODUCT_DETAIL_SCREEN)
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = product.image,
                    contentDescription = product.name,
                    loading = { LoadingAnimation() },
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(LightGreen)
                        .clickable {
                            onAddToCart(product)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "₺${product.price}",
                    color = DarkGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Divider(
                    color = GreyBorder,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = product.name ?: "",
                    color = Color.DarkGray,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
