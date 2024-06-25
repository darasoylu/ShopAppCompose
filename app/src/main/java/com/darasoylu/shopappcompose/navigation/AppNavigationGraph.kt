package com.darasoylu.shopappcompose.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.screens.CartScreen
import com.darasoylu.shopappcompose.screens.HomeScreen
import com.darasoylu.shopappcompose.screens.ProductDetailScreen
import com.darasoylu.shopappcompose.ui.theme.barColor

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.route != Routes.PRODUCT_DETAIL_SCREEN) {
                NavigationBar(
                    modifier = Modifier.height(70.dp),
                    containerColor = barColor
                ) {
                    listOfNavItems.forEach { navItem ->
                        val isSelected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true
                        val interactionSource = remember { MutableInteractionSource() }

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) {
                                        navController.navigate(navItem.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier.size(30.dp),
                                        imageVector = navItem.icon,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.Black else Color.Gray
                                    )
                                    Text(
                                        text = navItem.title,
                                        fontSize = 12.sp,
                                        color = if (isSelected) Color.Black else Color.Gray,
                                        modifier = Modifier
                                    )
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = barColor,
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME_SCREEN,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.HOME_SCREEN) {
                HomeScreen(navController)
            }
            composable(Routes.CART_SCREEN) {
                CartScreen(navController  )
            }
            composable(Routes.SETTINGS_SCREEN) {
                //SettingsScreen()
            }
            composable(Routes.PRODUCT_DETAIL_SCREEN) { backStackEntry ->
                val product = navController.previousBackStackEntry?.savedStateHandle?.get<ProductModel>("product")
                    ?: backStackEntry.savedStateHandle.get<ProductModel>("product")
                product?.let {
                    ProductDetailScreen(navController, it)
                }
            }
        }
    }
}