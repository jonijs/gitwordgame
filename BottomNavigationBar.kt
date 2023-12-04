package com.example.composewordgame1.screens

import android.provider.SyncStateContract
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.composewordgame1.R

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)


public object Constants {
    val BottomNavItems = listOf(

        BottomNavItem(
            label ="Search",
            icon = Icons.Filled.Search,
            route = "ZoekScherm"
        ),
        BottomNavItem(
            label = "Anagram",
            icon = Icons.Outlined.PlayArrow ,
            route = "AnagramScherm"
        ),
        BottomNavItem(
            label = "Game1",
            icon = Icons.Outlined.PlayArrow ,
            route = "Woord1Screen"
        ) ,

        BottomNavItem(
        label = "Game2",
        icon = Icons.Outlined.PlayArrow ,
        route = "ScrabbleScherm"
        ),
        BottomNavItem(
            label = "Game3",
        icon = Icons.Outlined.PlayArrow ,
        route = "Game3Scherm"
    )
    )
}



@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val mmap: Map<String, String> = mapOf("Search" to stringResource(R.string.Zoek), "Anagram" to stringResource(R.string.Anagram), "Game1" to stringResource(R.string.Game1), "Game2" to "Scrabble", "Game3" to "Game3")

    BottomNavigation(

        // set background color
        backgroundColor = Color(0xFF0F9D58)
    ) {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->

            // Place the bottom nav items
            BottomNavigationItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription =navItem.label)
                },

                // label
                label = {
                    mmap[navItem.label]?.let { Text(text = it) }
                },
                alwaysShowLabel = true
            )
        }
    }
}
