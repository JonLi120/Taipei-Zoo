package com.zoo.taipeizoo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zoo.taipeizoo.ui.home.Home

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Animal : Screen("animal")
    object Schedule : Screen("schedule")
}

@Composable
internal fun AppNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        addHomeTopLevel(navController, Screen.Home)
        addHomeTopLevel(navController, Screen.Animal)
        addHomeTopLevel(navController, Screen.Schedule)
    }
}

private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController,
    root: Screen
) {
    composable(root.route) {
        Home(name = root.route)
    }
}