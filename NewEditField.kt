package com.example.composewordgame1.screens

import androidx.compose.foundation.*
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
//import androidx.compose.material.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MyNewEditFieldx(
    text: String,
) {
    var txt by remember { mutableStateOf(text) }
    val scrollState = rememberScrollState()

    BasicTextField(

        value = txt,
        onValueChange = { txt = it },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(onNext = {}),
        decorationBox = {
            Row(Modifier.horizontalScroll(scrollState),horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                txt.forEachIndexed { index, char ->

                    DuckieTextFieldCharContainer(
                        text = char,
                        isFocused = false,
                    )
                }

            }
        },
    )
}

@Composable
private fun DuckieTextFieldCharContainer(
    modifier: Modifier = Modifier,
    text: Char,
    isFocused: Boolean,
) {

    val shape = remember { RoundedCornerShape(2.dp) }
    var focusRequester by remember { mutableStateOf(FocusRequester()) }
    var hasFocus by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .size(
                width = 20.dp,
                height = 40.dp,
            )
            .focusRequester(focusRequester)
            .clickable(
                enabled = true,
                onClick = { focusRequester.requestFocus() }
            )
            .background(
                color = Color(0x99F6F6F6),
                shape = shape,
            )
            .run {
                if (hasFocus) {
                    border(
                        width = 2.dp,
                        color = Color(0xA00F8300),
                        shape = shape,
                    )
                } else {
                    this
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text.toString())
    }
}

