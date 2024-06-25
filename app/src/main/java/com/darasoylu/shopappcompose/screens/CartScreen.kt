package com.darasoylu.shopappcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.darasoylu.shopappcompose.R
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.util.LoadingAnimation
import com.darasoylu.shopappcompose.util.formatPrice

val cartListProducts = listOf(
    ProductModel(
        "1",
        "Chair",
        "https://firebasestorage.googleapis.com/v0/b/shopapp-d549b.appspot.com/o/products%2Fchair.jpeg?alt=media&token=4a9df511-2c74-4b20-b152-cc2e27efa934",
        30.0,
        ""
    ),
    ProductModel(
        "2",
        "Motor",
        "https://firebasestorage.googleapis.com/v0/b/shopapp-d549b.appspot.com/o/products%2Fmotor.jpeg?alt=media&token=b06e776e-638b-44cb-ba2b-03156d4878d4",
        1500.0,
        ""
    ),
    ProductModel(
        "3",
        "Hat",
        "https://firebasestorage.googleapis.com/v0/b/shopapp-d549b.appspot.com/o/products%2Fhat.jpeg?alt=media&token=03dd3b69-6f57-4075-af6a-3651a3cf1ad1",
        10.0,
        ""
    )
)

@Composable
fun CartScreen(
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { it ->
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
                    .background(Color.White)
            ) {
                itemsIndexed(cartListProducts) { index, item ->
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(Color(0xFFEBEDF2), shape = RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            SubcomposeAsyncImage(
                                model = item.image,
                                contentDescription = null,
                                loading = { LoadingAnimation() },
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = item.name!!,
                                    fontSize = 28.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = { /* Azaltma işlemi */ },
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(Color.Black)
                                            .size(24.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_remove),
                                            contentDescription = "Decrease",
                                        )
                                    }
                                    Text(
                                        text = "1",
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp),
                                        fontSize = 25.sp
                                    )
                                    IconButton(
                                        onClick = { /* Artırma işlemi */ },
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(Color.Black)
                                            .size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Increase",
                                            tint = Color.White
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = stringResource(
                                        R.string.product_price,
                                        formatPrice(item.price!!)
                                    ),
                                    color = Color.Gray,
                                    fontSize = 24.sp,
                                )
                            }
                            IconButton(
                                onClick = { /* Çöp ikonuna tıklama işlemi */ }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(30.dp),
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .background(Color.White),
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .background(color = Color.Black, shape = RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Cart",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
