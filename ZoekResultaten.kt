package com.example.composewordgame1.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.tooling.data.EmptyGroup.name


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.*
import androidx.navigation.NavController
import com.example.composewordgame1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

var myReq : MyHttpRequest = MyHttpRequest("https://www.ensie.nl/betekenis")


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZoekResultaten(gevonden : List<String>, navController: NavController) {
    var strs by remember { mutableStateOf(arrayListOf("")) }
    var finalReqString by remember { mutableStateOf("empty") }
    val gevonden = gevonden.sorted()
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
        val spannedText = fromHtml(s, 0)
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
                    style = MaterialTheme.typography.h6
                )
            }
        }
        items(gevonden.size) {
            var txt = gevonden[it]
            var scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                     .horizontalScroll(scrollState)
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (it % 2 == 0) {
                    Text(
                        text = txt,
                        color = Color.Blue,
                        style = TextStyle(
                            color = Color.Blue,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W800,
                            fontStyle = FontStyle.Normal,
                            letterSpacing = 0.em,
                        ),
                        maxLines = 3,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                           // .clipScrollableContainer(orientation = Orientation.Horizontal)
                            .padding(horizontal = 10.dp)
                            .weight(1f)
                            .clickable(onClick = {
                                finalReqString = gevonden[it] }
                            )
                    )
                    if (it != gevonden.size - 1) {
                        Text(
                            text = gevonden[it + 1],
                            color = Color.Blue,
                            style = TextStyle(
                                color = Color.Blue,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.W800,
                                fontStyle = FontStyle.Normal,
                                letterSpacing = 0.em,

                                ),
                            maxLines = 3,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                               // .clipScrollableContainer(orientation = Orientation.Horizontal)
                                .padding(horizontal = 10.dp)
                                .weight(1f)
                                .clickable(onClick = {
                                    finalReqString = gevonden[it+1] })
                        )
                    }

                }
            }
        }

    }
}



