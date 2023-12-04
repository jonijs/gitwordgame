package com.example.composewordgame1.graphs.Navigation

sealed class Screens(val route : String){
    object PickLanguage : Screens("PickLanguage")
    object Woord1Screen : Screens("Woord1Screen")
    object HoofdScherm :  Screens("HoofdScherm")
    object AnagramScherm : Screens("AnagramScherm")
    object ZoekScherm : Screens("ZoekScherm")
    object ScrabbleScherm : Screens("ScrabbleScherm")
    object Game3Scherm: Screens(  "Game3Scherm")
}

