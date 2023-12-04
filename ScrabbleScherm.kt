package com.example.composewordgame1.screens

import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ScrabbleScherm(navController : NavController){


    var bevatLetters by rememberSaveable { mutableStateOf("") }
    var zoekpatroon by remember{ mutableStateOf(".*")}
    var  dosearch by remember { mutableStateOf("")}
    var zoekresultaat by rememberSaveable { mutableStateOf(arrayListOf(""))}
    var showDet by remember { mutableStateOf(false) }
    var opnieuw by remember { mutableStateOf(false) }

  LaunchedEffect(dosearch) {
        withContext(Dispatchers.IO) {
            if (!bevatLetters.toString().equals("")) {
                zoekresultaat.removeAll(zoekresultaat.toSet())
                dataStorage.WK.getScrabbleMatches(zoekpatroon.toUpperCase(), bevatLetters.toUpperCase()).forEach()  {
                    zoekresultaat.add(it)


                }
                showDet = true

            }
        }
  }

    Column(){
        Row(
            modifier = Modifier,
           horizontalArrangement=Arrangement.SpaceEvenly

        ){

            OutlinedTextField(
                value = zoekpatroon,
                onValueChange = { zoekpatroon = it },

                label = { Text("zoekpatroon") },
                modifier=Modifier.size( width=120.dp,height = 60.dp)
            )
            OutlinedTextField(
                value = bevatLetters,
                onValueChange = { bevatLetters = it },
                label = { Text("Contains") },
                        modifier=Modifier.size( width=120.dp,height = 60.dp)
            )

            Button(onClick={

                zoekresultaat.removeAll(zoekresultaat.toSet())
                dosearch = bevatLetters
                showDet = false
             //  dataStorage.WK.getScrabbleMatches(zoekpatroon.toUpperCase(), bevatLetters.toUpperCase()).forEach() {
              //      zoekresultaat.add(it)


               // }

            }

            )

            {
                Text("Zoek")
            }

        }

       if( showDet){
           ScrabbleResultaten(zoekresultaat, navController)
           opnieuw = true
       }
        if( opnieuw && !showDet){
            ScrabbleResultaten(zoekresultaat, navController)
            opnieuw = false
        }
    }
}


