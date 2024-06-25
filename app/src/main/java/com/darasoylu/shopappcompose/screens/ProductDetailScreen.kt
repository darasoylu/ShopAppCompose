package com.darasoylu.shopappcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.darasoylu.shopappcompose.ui.theme.barColor
import com.darasoylu.shopappcompose.ui.theme.starColor
import com.darasoylu.shopappcompose.util.formatPrice
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    navController: NavController,
    product: ProductModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(barColor)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                    )
                }

                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.Black,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(barColor)
                    .height(400.dp),
            ) {
                SubcomposeAsyncImage(
                    model = product.image,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                        .clip(shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier,
                            text = product.name!!,
                            color = Color.Black,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = starColor,
                                modifier = Modifier.size(30.dp)
                            )

                            Spacer(modifier = Modifier.width(3.dp))

                            Text(
                                text = stringResource(
                                    R.string.product_detail_star
                                ),
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            Text(
                                text = stringResource(
                                    R.string.product_detail_review
                                ),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        modifier = Modifier,
                        text = stringResource(
                            R.string.product_price,
                            formatPrice(product.price!!)
                        ),
                        color = Color.DarkGray,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 26.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier
                            .padding(end = 15.dp),
                        text = stringResource(
                            R.string.product_detail_desc
                        ),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    ) {
                        val context = LocalContext.current

                        Button(
                            onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Added",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 15.dp)
                                .background(color = Color.Black, shape = RoundedCornerShape(10.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Add to Cart",
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
    }
}