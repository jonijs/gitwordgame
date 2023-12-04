package com.example.composewordgame1.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.example.composewordgame1.AnagramData
import com.example.composewordgame1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun AnagramScherm(navController: NavController) {
    var aantAnagrammen by remember { mutableStateOf("2") }
    var finalaantAnagrammen by remember { mutableStateOf("2") }
    val aList: ArrayList<AnagramData> = arrayListOf()
    var anagramList = remember { mutableStateListOf<AnagramData>() }
    var strs by remember { mutableStateOf(arrayListOf("")) }
    var i = 0
    var finalReqString by remember { mutableStateOf("") }
    var showDet by remember { mutableStateOf(false) }
    var changed by remember { mutableStateOf(true) }
    var finalaantLetters by remember { mutableStateOf("0") }
    var aantLetters by remember { mutableStateOf("0") }
    var gegevensOphalen by remember { mutableStateOf(false) }

    @Composable
    fun AnaItem(ad: AnagramData) {
        val scrollState1 = rememberScrollState()

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState1)
                .padding(5.dp)
        ) {
            BasicTextField(value = ad.letters, onValueChange = {}, Modifier.padding(1.dp))
            ad.woorden.forEach() {
                Text(
                    text = it,
                    color = Color.Blue,

                    style = TextStyle(
                        color = Color.Blue,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.W800,
                        fontStyle = FontStyle.Normal,
                        letterSpacing = 0.em,
                    ),
                    maxLines = 3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(onClick = {
                            finalReqString = it
                        })
                        .padding(horizontal = 2.dp)

                )

            }
        }
    }

    if (gegevensOphalen) {
        LaunchedEffect(key1 = finalaantAnagrammen + finalaantLetters) {
            withContext(Dispatchers.IO) {
                if (aantAnagrammen != "0") {
                    anagramList.clear()
                    dataStorage.WK.findAllAnagramsOfAantal(finalaantAnagrammen.toInt())
                        .forEach() {
                            if (it.letters.length == aantLetters.toInt() || finalaantLetters.toInt() == 0)
                                anagramList.add(it)
                        }

                }
            }
        }
    }


    LaunchedEffect(finalReqString) {
        withContext(Dispatchers.IO) {
            if (!finalReqString.toString().equals("empty")) {
                strs = myReq.MakeRequest(finalReqString)

                showDet = true
            }
        }
    }

    Column() {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = aantAnagrammen,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = {
                    aantAnagrammen = it
                    gegevensOphalen = false
                },
                label = { androidx.compose.material3.Text(stringResource(R.string.aantalAnagrammen)) },
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth(0.4f)

            )
            OutlinedTextField(
                value = anagramList.size.toString(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = {

                },
                label = { androidx.compose.material3.Text(stringResource(R.string.aantalAnagrammen)) },
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth(0.4f)

            )
            OutlinedTextField(
                value = aantLetters.toString(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = {
                    aantLetters = it
                    gegevensOphalen = false
                },
                label = { androidx.compose.material3.Text(stringResource(R.string.aantalLetters)) },
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth(0.4f)

            )
            Button(
                onClick = {
                    gegevensOphalen = true
                    finalaantAnagrammen = aantAnagrammen
                    finalaantLetters = aantLetters
                }
            ) {
                Text("Get")
            }
        }
        if (showDet && changed) {
            var s = ""
            strs.forEach() { s = s + it + '\n' }
            val spannedText = HtmlCompat.fromHtml(s, 0)
            //DetailPopup(strs[0])
            DetailPopup(spannedText, 250.dp)
        }
        LazyColumn() {
            items(anagramList.size) {
                AnaItem(anagramList[it])
            }
        }


    }


}