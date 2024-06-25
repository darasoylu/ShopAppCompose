package com.darasoylu.shopappcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)

val listOfNavItems = listOf(
    NavItem(
        route = "HOME",
        title = "Home",
        icon = Icons.Filled.Home
    ),
    NavItem(
        route = "CART",
        title = "Cart",
        icon = Icons.Filled.ShoppingCart,
    ),
    NavItem(
        route = "SETTINGS",
        title = "Settings",
        icon = Icons.Filled.Settings,
    )
)