package com.example.composewordgame1.screens

import android.content.Context
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.drawable.Icon
import android.util.Log.v
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.*
import androidx.compose.foundation.*
import androidx.compose.material.Button
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.foundation.layout.*


//import androidx.compose.foundation.layout.BoxScopeInstance.align


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.max
import androidx.core.text.HtmlCompat

import androidx.navigation.NavHostController
import com.example.composewordgame1.AnagramData
import com.example.composewordgame1.R
import com.example.composewordgame1.WoordKaart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

@Composable
private fun MyEditField(sze: Int = 8) {
    var placeholder: String = " $sze letters"
    var svalue by remember {
        mutableStateOf("")
    }
///size bepalen
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    var wijdte = with(density) {
        textMeasurer.measure(
            "W".repeat(sze), style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        ).size.width.toDp()
    }


    BasicTextField(
        value = svalue,
        onValueChange = { newText ->
            if (newText.length <= sze)
                svalue = newText
        },
        // maxlines = 2,
        // singleLine=false,
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            baselineShift = BaselineShift.Subscript

        ),
        modifier = Modifier.size(width = wijdte, height = 50.dp),


        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .size(
                        width = max(wijdte, 40.dp),
                        height = 42.dp
                    )

                    .padding(horizontal = 5.dp, vertical = 5.dp) // margin left and right
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(size = 5.dp)
                    )
            ) {
                if (svalue.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Blue,
                        textAlign = TextAlign.Start

                    )

                }
                innerTextField()
            }

        }

    )
}

@Composable
fun AddAnother(size: Int) {
    var nogeen by remember { mutableStateOf(true) }

    MyEditField(sze = size)
    if (nogeen) {
        Box(
            modifier = Modifier
                .padding(start = 1.dp)
                .size(width = 30.dp, height = 30.dp)
                .clickable { nogeen = false }, contentAlignment = Alignment.BottomStart
        )
        {
            Text("+", fontSize = 20.sp)
        }
    }
    if (!nogeen) {
        AddAnother(size)
    }


}

@Composable
fun AddAnotherWord(opnieuw: Boolean = false, size: Int) {
    var addAnother by remember { mutableStateOf(false) }
    var reset by remember { mutableStateOf(false) }



    Column(modifier = Modifier) {
        var nieuwWoord by remember { mutableStateOf("") }
        var removeall by remember { mutableStateOf(false) }
        if (!reset) {
            @OptIn(
                ExperimentalLayoutApi::class
            )
            FlowRow(
                modifier = Modifier
                    .padding(4.dp),
                //.wrapContentWidth(),
                maxItemsInEachRow = 4
            ) {
                AddAnother(size = size)

            }
        } else {
            @OptIn(
                ExperimentalLayoutApi::class
            )
            FlowRow(
                modifier = Modifier.padding(4.dp),
                maxItemsInEachRow = 4
            ) {
                AddAnother(size = size)
            }
        }
    }

}


@Composable
fun Game3Scherm(navController: NavHostController) {
    var aantL by remember { mutableStateOf("2") }
    var ltrs by remember { mutableStateOf("") }
    var aantXtra by remember { mutableStateOf("0") }
    var allWords = remember { mutableStateListOf<String>() }
    var resetWrdInvoer by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val getletters = {
        resetWrdInvoer = !resetWrdInvoer
        allWords.clear()
        if (!aantL.isNullOrEmpty() && aantL.toInt() != 0) {
            ltrs = dataStorage.WK.getRandomStringOfLength(aantL.toInt() ?: 0)
        }
    }
    var fromComputer by remember { mutableStateOf(true) }
    var showFromComputer by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val x = input4Game3()
        aantL = x.aant.toString()
        aantXtra = x.extra.toString()
        Row(modifier = Modifier)
        {
            Button(
                modifier = Modifier
                    .height(40.dp)
                    .align(alignment = Alignment.CenterVertically), onClick = getletters
            )
            {
                Text(
                    "get Letters",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 15.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(width = 1.dp, color = Color.Red)
                    .padding(horizontal = 10.dp)
            ) {
                BasicTextField(
                    value = ltrs,
                    onValueChange = {
                        ltrs = it
                    },
                    readOnly = true,
                    //  isError = ltrs.isNullOrEmpty(),

                    modifier = Modifier
                        .size(width = 120.dp, height = 40.dp)
                        .align(alignment = Alignment.Center)
                        .wrapContentSize()
                )
            }
        }
        if (resetWrdInvoer) {
            AddAnotherWord(size = aantL.toInt())
        } else {
            AddAnotherWord(size = aantL.toInt())
        }
        if (aantXtra.toInt() >= 1) {
            if (resetWrdInvoer) {
                AddAnotherWord(size = aantL.toInt() + 1)
            } else {
                AddAnotherWord(size = aantL.toInt() + 1)
            }
        }
        if (aantXtra.toInt() == 2) {
            if (resetWrdInvoer) {
                AddAnotherWord(size = aantL.toInt() + 2)
            } else {
                AddAnotherWord(size = aantL.toInt() + 2)
            }
        }

        Button(modifier = Modifier, onClick = { fromComputer = true })
        {
            Text("Computer zegt")
            /*
            Spacer(modifier = Modifier
                .width(30.dp)
                .background(color = MaterialTheme.colorScheme.background))


            Text(color=Color.Magenta,text=ltrs,modifier=Modifier.background(color = Color.White))
            */

        }
        if (fromComputer) {
            Text("loading")

            allWords.clear()
            LaunchedEffect(key1 = ltrs + aantXtra) {
                dataStorage.WK.getFomLetterAndExtra(ltrs, aantXtra.toInt())
                    .forEach() { it -> allWords.add(it) }
                var si = allWords.size
                fromComputer = false
                if (allWords.size > 0) showFromComputer = true

            }

        }

        if (showFromComputer) {
            /*
            LazyColumn( ){
                items(allWords.size){
                    WoordItem(allWords[it])

                }
            }

             */
////////////////////////////////////////////////////////////
            //  MyGrid(allWords)
            ////////////////////////////////////////////

        }
        var lengte = aantL.toInt()
        var xtral = aantXtra.toInt()
        MyGrid(allWords, lengte, xtral)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyGrid(items: List<String>, len: Int = 0, xtral: Int = 0) {
    var finalReqString by remember { mutableStateOf("empty") }
    var strs by remember { mutableStateOf(arrayListOf("")) }
    var showDet by remember { mutableStateOf(false) }
    var woordtosearch by remember { mutableStateOf("") }

    @Composable
    fun WoordItem(ad: String) {
        val scrollState1 = rememberScrollState()

        LaunchedEffect(finalReqString) {
            withContext(Dispatchers.IO) {
                if (!finalReqString.toString().equals("empty")) {
                    strs = myReq.MakeRequest(finalReqString)
                    showDet = true
                } else showDet = false
            }

        }

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState1)

                .padding(5.dp)
        ) {
            BasicTextField(value = ad, onValueChange = {}, Modifier.padding(1.dp))
            {
                Text(
                    text = ad,
                    color = Color.Black,

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
                    modifier = Modifier.
                        clickable(onClick = {
                            finalReqString = ad
                        }
                        )
                        .padding(horizontal = 2.dp)

                )

            }

        }
    }
    

    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //

        items(items.sorted().filter { it.length == len }.size) { text ->
            WoordItem(items.sorted().filter { it.length == len }[text])
            // Text(text = items[text])
        }

    }

    Spacer(modifier = Modifier.height(15.dp))

    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //

        items(items.sorted().filter { it.length == len + 1 }.size) { text ->
            WoordItem(items.sorted().filter { it.length == len + 1 }[text])
            // Text(text = items[text])
        }

    }

    Spacer(modifier = Modifier.height(15.dp))
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //

        items(items.sorted().filter { it.length == len + 2 }.size) { text ->
            WoordItem(items.sorted().filter { it.length == len + 2 }[text])
            // Text(text = items[text])
        }

    }
    if (showDet) {
        var s = ""
        val closepopup = {
            showDet = false
            finalReqString = "empty"
        }
        strs.forEach() { s = s + it + '\n' }
        val spannedText = HtmlCompat.fromHtml(s, 0)
        //DetailPopup(strs[0])
        DetailPopup2(finalReqString, spannedText, popupcontrol = true, closePopUp = closepopup)
    }

}


data class nums(val aant: Int, val extra: Int)


@Composable
fun input4Game3(): nums {
    var aantLetters by remember { mutableStateOf("0") }
    var aantExtra by remember { mutableStateOf("0") }

    val rbuttons = listOf(0, 1, 2)
    var selectedButton = remember { mutableStateOf(rbuttons.first()) }
    val colors = RadioButtonDefaults.colors( // 4
        selectedColor = colorResource(
            id =
            R.color.colorPrimary
        ),
        unselectedColor = colorResource(
            id =
            R.color.colorPrimaryDark
        ),
        disabledColor = Color.LightGray
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 5.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Black)
            )

    ) {

        OutlinedTextField(
            aantLetters,
            onValueChange = { aantLetters = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("#letters") },
            modifier = Modifier
                .size(width = 120.dp, height = 60.dp)
                .padding(2.dp)
        )


        Spacer(modifier = Modifier.width(30.dp))

        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .border(width = 1.dp, color = Color.White)
                .size(width = 200.dp, height = 40.dp)
        ) {
            Row(modifier = Modifier) {
                rbuttons.forEach() { index ->
                    val isSelected = index == selectedButton.value
                    RadioButton( // 5
                        modifier = Modifier.padding(top = 10.dp),
                        colors = colors,
                        selected = isSelected,
                        onClick = {
                            selectedButton.value = index
                            aantExtra = index.toString()
                        } // 6
                    )
                    Text(
                        index.toString() + "    ", modifier = Modifier
                            //.align(Alignment.CenterVertically)
                            .padding(start = 0.dp, end = 0.dp, top = 15.dp)
                    )
                }
            }
        }


    }

    if (!aantLetters.isNullOrEmpty()) {
        return nums(aantLetters.toInt(), aantExtra.toInt())
    } else
        return nums(0, 0)
}



