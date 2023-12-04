package com.example.composewordgame1.screens


import androidx.compose.foundation.*


//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowAndSelectLetters(rnr : Int, aantLetters : Int, letters2 :Letters) {

    var woord = letters2.letters
    val shape = remember { RoundedCornerShape(2.dp) }
    var letters: MutableList<LetterInfo> = ArrayList()
    var sortedLetters: MutableList<LetterInfo> = ArrayList()
    var emptyLetters: MutableList<Char> = ArrayList()
    woord.forEachIndexed() { ndx, it ->
        letters.add(LetterInfo(it, ndx))
        sortedLetters.add(LetterInfo(it, ndx))
        emptyLetters.add('?'.toChar())
    }

    sortedLetters.sortBy { it.letter }

    Column(modifier = Modifier) {

        var remLetters by remember { mutableStateOf(emptyLetters) }
        var chars by remember { mutableStateOf(sortedLetters) }
        val enteredChars = remember { mutableStateListOf<Char>() }

        if (enteredChars.size == 0) {
            for (i in 1..woord.length) {
                enteredChars.add("_".last())
            }
        }
        val scrollState1 = rememberScrollState()
        Row(modifier = Modifier.horizontalScroll(scrollState1)) {

            for (i in 1..woord.length) {
                //var chari by remember { mutableStateOf(sortedLetters[i-1].letter) }
                var colorN by remember { mutableStateOf(Color.Cyan) }
                val focusManager = LocalFocusManager.current
                var breedte = 20.dp

                BasicTextField(value = chars[i - 1].letter.toString(),
                    onValueChange = { },
                    modifier = Modifier
                        .padding(1.dp)
                        .width(breedte)
                        .height(28.dp),
                    decorationBox = {
                        Box(
                            modifier = Modifier
                                .size(width = 20.dp, height = 30.dp)
                                .onFocusChanged {
                                    colorN = if (it.isFocused) Color.Green else Color.Black
                                }
                                .clickable {
                                    colorN = Color.Red
                                    var ndx = chars[i - 1].ndx
                                    enteredChars[ndx] = chars[i - 1].letter
                                    colorN = Color.Magenta

                                }
                                .border(
                                    width = 1.dp,
                                    color = colorN,
                                    shape = shape,
                                ),
                            contentAlignment = androidx.compose.ui.Alignment.Center,

                            ) {
                            Text(
                                text = chars[i - 1].letter.toString()
                            )
                        }
                    }

                )
            }


        }
        //SpecialLayout() {
            val scrollState2 = rememberScrollState()
            Row(modifier = Modifier.horizontalScroll(scrollState2)) {

                for (i in 1..woord.length) {
                    //var chari by remember { mutableStateOf(sortedLetters[i-1].letter) }
                    var colorN by remember { mutableStateOf(Color.Cyan) }
                    val focusManager = LocalFocusManager.current
                    var breedte = 20.dp

                    BasicTextField(value = enteredChars[i - 1].toString(),
                        onValueChange = {
                            enteredChars[i - 1] = it.filter { s -> s != '_' }.last()
                        },

                        modifier = Modifier
                            .padding(1.dp)
                            .width(breedte)
                            .height(28.dp),
                        decorationBox = {
                            Box(
                                modifier = Modifier
                                    .size(width = 20.dp, height = 30.dp)
                                    .onFocusChanged {
                                        colorN = if (it.isFocused) Color.Green else Color.Black
                                    }

                                    .border(
                                        width = 1.dp,
                                        color = colorN,
                                        shape = shape,
                                    ),
                                contentAlignment = androidx.compose.ui.Alignment.Center,

                                ) {
                                Text(
                                    text = enteredChars[i - 1].toString()
                                )
                            }
                        }

                    )
                }
            }
       // }
    }
}

