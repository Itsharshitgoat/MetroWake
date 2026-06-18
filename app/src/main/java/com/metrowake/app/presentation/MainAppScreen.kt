package com.metrowake.app.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.metrowake.app.presentation.home.HomeScreen
import com.metrowake.app.presentation.journey.JourneyScreen
import com.metrowake.app.presentation.profile.ProfileScreen
import com.metrowake.app.presentation.routes.RoutesScreen

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Routes : Screen("routes", "Routes", Icons.Filled.Map)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)
    object Journey : Screen("journey", "Journey", Icons.Filled.Map) // Base route
}

@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(Screen.Home, Screen.Routes, Screen.Profile)

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            // Only show bottom bar on main screens
            if (currentDestination?.route in bottomNavItems.map { it.route }) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = MaterialTheme.colorScheme.secondary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Routes.route) { RoutesScreen(navController) }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(
                route = "${Screen.Journey.route}/{startId}/{destId}",
                arguments = listOf(
                    navArgument("startId") { type = NavType.StringType },
                    navArgument("destId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val startId = backStackEntry.arguments?.getString("startId") ?: return@composable
                val destId = backStackEntry.arguments?.getString("destId") ?: return@composable
                JourneyScreen(navController, startId, destId)
            }
        }
    }
}
