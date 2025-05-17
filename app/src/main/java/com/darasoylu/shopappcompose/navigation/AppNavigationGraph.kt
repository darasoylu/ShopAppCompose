package com.darasoylu.shopappcompose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.presentation.components.PromotionDialog
import com.darasoylu.shopappcompose.presentation.screen.card_screen.CardScreen
import com.darasoylu.shopappcompose.presentation.screen.home_screen.HomeScreen
import com.darasoylu.shopappcompose.presentation.screen.product_detail_screen.ProductDetailScreen
import com.darasoylu.shopappcompose.presentation.screen.search_screen.SearchScreen
import com.darasoylu.shopappcompose.presentation.theme.PrimaryGreen
import com.darasoylu.shopappcompose.presentation.viewmodel.SharedViewModel

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = hiltViewModel()
    var showPromotionDialog by remember { mutableStateOf(false) }

    // Track current route to determine if bottom bar should be shown
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // List of routes where bottom bar should be hidden
    val bottomBarHiddenRoutes = listOf(Routes.PRODUCT_DETAIL_SCREEN, Routes.CARD_SCREEN)
    val shouldShowBottomBar = currentRoute !in bottomBarHiddenRoutes

    // Fetch remote configs when the app starts
    LaunchedEffect(key1 = Unit) {
        sharedViewModel.fetchRemoteConfigs()
    }

    // Set promotion dialog visibility based on feature flag
    LaunchedEffect(key1 = sharedViewModel.isFeatureEnabled) {
        if (sharedViewModel.isFeatureEnabled) {
            showPromotionDialog = true
        }
    }

    // Show promotion dialog if enabled and data exists
    if (showPromotionDialog && sharedViewModel.isFeatureEnabled && sharedViewModel.promotionData != null) {
        PromotionDialog(
            promotionData = sharedViewModel.promotionData!!,
            onDismiss = { showPromotionDialog = false }
        )
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomAppBar(
                    modifier = Modifier.height(70.dp),
                    containerColor = Color.White,
                    contentColor = PrimaryGreen,
                    tonalElevation = 0.dp
                ) {

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItem ->
                        val isSelected =
                            currentDestination?.hierarchy?.any { it.route == navItem.route } == true

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
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
                                imageVector = navItem.icon,
                                contentDescription = null,
                                tint = if (isSelected) PrimaryGreen else Color.Gray,
                                modifier = Modifier.size(35.dp)
                            )

                            // Indicator line
                            if (isSelected) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(4.dp)
                                        .background(
                                            color = PrimaryGreen,
                                            shape = RoundedCornerShape(1.5.dp)
                                        )
                                )
                            } else {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
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
                HomeScreen(navController, sharedViewModel = sharedViewModel)
            }
            composable(Routes.SEARCH_SCREEN) {
                SearchScreen(navController)
            }
            composable(Routes.PROFILE_SCREEN) {
                //ProfileScreen()
            }
            composable(Routes.PRODUCT_DETAIL_SCREEN) { backStackEntry ->
                val product =
                    navController.previousBackStackEntry?.savedStateHandle?.get<ProductModel>("product")
                        ?: backStackEntry.savedStateHandle.get<ProductModel>("product")
                product?.let {
                    ProductDetailScreen(navController, product = it, sharedViewModel = sharedViewModel)
                }
            }
            composable(Routes.CARD_SCREEN) {
                CardScreen(navController, sharedViewModel = sharedViewModel)
            }
        }
    }
}