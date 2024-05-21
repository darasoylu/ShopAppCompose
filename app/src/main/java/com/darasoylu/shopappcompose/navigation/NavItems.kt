package com.darasoylu.shopappcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.Settings
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
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Filled.Person,
        selectedIcon = Icons.Default.Person
    ),
    NavItem(
        route = "SETTINGS",
        title = "SETTINGS",
        icon = Icons.Filled.Settings,
        selectedIcon = Icons.Default.Settings
    )
)