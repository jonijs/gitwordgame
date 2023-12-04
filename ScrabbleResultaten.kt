package com.example.composewordgame1.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.example.composewordgame1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ScrabbleResultaten(gevonden : List<String>, navController: NavController){
    val gevonden = gevonden.sortedBy { it.length }
    var finalReqString by remember { mutableStateOf("empty") }
    var strs by remember { mutableStateOf(arrayListOf("")) }
    var showDet by remember { mutableStateOf(false) }

    LaunchedEffect(finalReqString) {
        withContext(Dispatchers.IO) {
            if (!finalReqString.toString().equals("empty")) {
                strs = myReq.MakeRequest(finalReqString)

                showDet = true
            }

        }

    }

    if (showDet) {
        var s =""
        strs.forEach(){ s = s + it + '\n'}
        val spannedText = HtmlCompat.fromHtml(s, 0)
        //DetailPopup(strs[0])
        DetailPopup(spannedText)
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(6.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.gevonden),
                    style = MaterialTheme.typography.h5
                )
            }
        }
        items(gevonden.size) {
            var txt = gevonden[it]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                    Text(
                        text = txt,
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
                            .padding(horizontal = 10.dp)
                            .weight(1f)

                            .clickable(onClick = {
                                finalReqString = gevonden[it] })

                    )

                }
            }
        }

    }


