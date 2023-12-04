package com.example.composewordgame1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.example.composewordgame1.AnagramData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


var rem : List<String> = arrayListOf()


@Composable
fun ShowLetters(aantLetters : Int, aantAnagrams : Int ){
    var visible = remember { mutableStateOf(false) }
    var data = dataStorage.WK.findAllAnagramsOfAantalLengte(aantAnagrams, aantLetters)

    var randNr = (Math.random() * data.size).toInt()

    if( data.size > 0) {
        var woords = data.get(randNr).woorden
        rem = woords

        var txt = data.get(randNr).letters

        if(woords.size > 0) {
            dataStorage.setWrden(woords as MutableList<String>)

            Text(
                text = txt,
                modifier = Modifier
            )


        }

    }
    /*
    else {
        dataStorage.woorden = ""
    }
    */

}

