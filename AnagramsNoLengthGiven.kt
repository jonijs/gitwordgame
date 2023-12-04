package com.example.composewordgame1

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.composewordgame1.screens.dataStorage
import com.example.composewordgame1.screens.rem


@Composable
fun AnagramsNoLengthGiven( aantAnagrams : Int ){
   // var visible = remember { mutableStateOf(false) }
    var data = dataStorage.WK.findAllAnagramsOfAantal(aantAnagrams)
    var randNr = (Math.random() * data.size).toInt()
    var wrd =  { mutableStateOf<String>("") }
    if( data.size > 0) {
        var woords = data.get(randNr).woorden as MutableList<String>
        //rem = woords

        var txt = data.get(randNr).letters

        if(woords.size > 0) {
            dataStorage.setWrden(woords)
            Text(
                text = txt,
                modifier = Modifier
            )
        }

    }/*
        dataStorage.woorden.
    }
    */
}