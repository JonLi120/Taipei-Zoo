package com.zoo.taipeizoo.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zoo.taipeizoo.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun AppBarWidget(title: MutableState<String>) {
    TopAppBar(
        title = {
            Text(text = title.value)
        },
        elevation = 4.dp
    )
}

@Composable
fun BottomNavigationWidget(navController: NavHostController, appBarTitle: MutableState<String>) {

    val currentSelectedItem by navController.currentScreenAsState()

    BottomNavigation(elevation = 4.dp) {
        NavigationItems.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                       when(index) {
                           0 -> Icon(imageVector = Icons.Filled.Home, contentDescription = null)
                           1 -> Icon(imageVector = Icons.Filled.Pets, contentDescription = null)
                           else -> Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = null)
                       }
                },
                label = { Text(text = item.label) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                selected = currentSelectedItem == item.screen,
                onClick = { 
                    navController.navigate(item.screen.route) {
                        launchSingleTop = true
                        restoreState = true
                        
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                    appBarTitle.value = item.label
                }
            )
        }
    }
}

@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.route == Screen.Home.route -> {
                    selectedItem.value = Screen.Home
                }
                destination.route == Screen.Animal.route -> {
                    selectedItem.value = Screen.Animal
                }
                destination.route == Screen.Schedule.route -> {
                    selectedItem.value = Screen.Schedule
                }
            }
        }

        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val appBarTitle = remember { mutableStateOf(NavigationItems[0].label) }

    Scaffold(
        topBar = { AppBarWidget(appBarTitle) },
        bottomBar = { BottomNavigationWidget(navController, appBarTitle) }
    ) {
        AppNavigation(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "DarkMode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppBarWidget() {
    AppTheme {
        MainScreen()
    }
}

data class MainNavigationItem(val screen: Screen, val imageVector: ImageVector, val label: String)

private val NavigationItems = listOf(
    MainNavigationItem(Screen.Home, Icons.Filled.Home, "館區簡介"),
    MainNavigationItem(Screen.Animal, Icons.Filled.Pets, "動物資料"),
    MainNavigationItem(Screen.Schedule, Icons.Filled.CalendarToday, "行事曆")
)
