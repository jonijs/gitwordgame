package com.example.composewordgame1.screens

import androidx.compose.foundation.*


import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RowScrollableEditFields(
    rnr: Int,
    aantLetters: Int,
    letters: String,
    changed: Boolean
) {

    val shape = remember { RoundedCornerShape(2.dp) }
    var scrollState = rememberScrollState()
    var focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier.scrollable(scrollState, orientation = Orientation.Horizontal)
    )
    {
        var currentField by remember { mutableStateOf(0) }
        for (i in 1..aantLetters) {
            // val focusRequester = remember { FocusRequester() }
            //
            var ltr by remember { mutableStateOf("") }
            var breedte = 20.dp
            var colorN by remember { mutableStateOf(Color.Cyan) }
8

            BasicTextField(
                singleLine = true,
                value = if (ltr.length > 1) {
                    ltr.substring(ltr.length - 1)
                } else {
                    ltr
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text

                ),
                onValueChange = {
                    if (ltr.isNullOrEmpty()) {
                        ltr = it

                    } else {
                        ltr = it.substring(it.length - 1)
                    }
                    focusManager.moveFocus(FocusDirection.Next)
                },

                modifier = Modifier
                    .padding(1.dp)


                    // .focusRequester(focusRequester)
                    .onFocusEvent {
                        keyboardController?.show()

                        colorN = if (it.isFocused) {
                            Green
                        } else Black
                        currentField = i
                    }
                    .width(breedte)
                    .height(28.dp)
                    .background(color = Color.LightGray),
                decorationBox = {
                    Box(
                        modifier = Modifier
                            .size(width = 20.dp, height = 30.dp)
                            .onFocusChanged {
                                colorN = if (it.isFocused) Green else Black
                            }
                            .border(
                                width = 1.dp,
                                color = colorN,
                                shape = shape,
                            ),
                        contentAlignment = androidx.compose.ui.Alignment.Center,

                        ) {
                        Text(
                            text = ltr
                        )
                    }
                }
            )
            /////////////////////////////
            ////////////////////////////


        }


    }
}
