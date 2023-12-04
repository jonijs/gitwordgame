package com.example.composewordgame1.graphs.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composewordgame1.screens.*


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(navController : NavController){
    val navController = rememberNavController()


    NavHost(navController =  navController, startDestination = Screens.PickLanguage.route){

        composable(route = Screens.PickLanguage.route){
           PickLanguage(navController)
        }

        composable(route = Screens.HoofdScherm.route){
            HoofdScherm(navController)
        }
        composable(route = Screens.Woord1Screen.route){
           Woord1Screen(navController)
        }
        composable(route = Screens.AnagramScherm.route){
            AnagramScherm(navController)
        }
        composable(route = Screens.ZoekScherm.route){
            ZoekScherm(navController)
        }
        composable(route = Screens.ScrabbleScherm.route) {
            ScrabbleScherm(navController)
        }
        composable(route = Screens.Game3Scherm.route){
            Game3Scherm(navController)
        }
    }
}