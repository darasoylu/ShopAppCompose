package com.darasoylu.shopappcompose.navigation

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.screens.HomeScreen
import com.darasoylu.shopappcompose.screens.ProductDetailScreen

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(100.dp),
                containerColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
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
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            )
                        }
                    )
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
                Log.i("lknasdasd2", "home")
                HomeScreen(navController)
            }
            composable(Routes.PROFILE_SCREEN) {
                //ProfileScreen()
            }
            composable(Routes.SETTINGS_SCREEN) {
                //SettingsScreen()
            }
            composable(Routes.PRODUCT_DETAIL_SCREEN) {

                val product = navController.previousBackStackEntry?.savedStateHandle?.get<ProductModel>("product")!!
                product.let {
                    Log.i("absdasd", it.toString())
                    ProductDetailScreen(navController, it)
                }

            }
        }
    }
}