package com.darasoylu.shopappcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)

val listOfNavItems = listOf(
    NavItem(
        route = "HOME",
        title = "HOME",
        icon = Icons.Filled.Home,
        selectedIcon = Icons.Default.Home
    ),
    NavItem(
        route = "SEARCH",
        title = "SEARCH",
        icon = Icons.Filled.Search,
        selectedIcon = Icons.Default.Search
    ),
    NavItem(
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Filled.Person,
        selectedIcon = Icons.Default.Person
    ),
)