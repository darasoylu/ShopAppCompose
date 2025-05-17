package com.darasoylu.shopappcompose.presentation.screen.product_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.presentation.components.LoadingAnimation
import com.darasoylu.shopappcompose.presentation.theme.BackgroundGreen
import com.darasoylu.shopappcompose.presentation.theme.DarkGreen
import com.darasoylu.shopappcompose.presentation.theme.HeartRed
import com.darasoylu.shopappcompose.presentation.theme.PrimaryGreen
import com.darasoylu.shopappcompose.presentation.viewmodel.SharedViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProductDetailScreen(
    navController: NavController,
    product: ProductModel,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    var isFavorite by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
            .verticalScroll(scrollState)
    ) {
        // Top Bar with back button and favorite icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            
            Text(
                text = "Product Details",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
            
            IconButton(
                onClick = { isFavorite = !isFavorite },
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) HeartRed else Color.Gray
                )
            }
        }
        
        // Product Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White)
        ) {
            SubcomposeAsyncImage(
                model = product.image,
                contentDescription = product.name,
                loading = { LoadingAnimation() },
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
            
            // Price tag
            Card(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .shadow(4.dp, RoundedCornerShape(8.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = DarkGreen
                )
            ) {
                Text(
                    text = "₺${product.price}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        // Product Info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Product Name and Add to Cart button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Product Name
                Text(
                    text = product.name ?: "Product Name",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.weight(1f)
                )
                
                // Add to Cart Button
                Button(
                    onClick = { sharedViewModel.addToCart(product) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryGreen
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Add",
                        fontSize = 14.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Product Code/ID
            Text(
                text = "Product ID: ${product.productId ?: "N/A"}",
                color = Color.Gray,
                fontSize = 14.sp,
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Divider()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Overview Section
            Text(
                text = "Overview",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "This premium product is designed with quality materials and craftsmanship. It offers exceptional value and performance for everyday use. The elegant design combines functionality with aesthetics, making it a perfect addition to your collection. We guarantee customer satisfaction with this product's durability and performance.",
                color = Color.DarkGray,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Justify
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Divider()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Additional Details
            Text(
                text = "Additional Details",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Display product details in rows
            DetailRow("Category", "Furniture")
            DetailRow("Availability", "In Stock")

        }

    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp,
        )
        Text(
            text = value,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
    
    Spacer(modifier = Modifier.height(4.dp))
}