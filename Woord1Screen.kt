    package com.example.composewordgame1.screens
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.focus.FocusRequester
    import androidx.compose.ui.focus.focusRequester
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.text.input.TextFieldValue
    import androidx.compose.ui.text.style.TextDecoration
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.window.Popup
    import androidx.compose.ui.window.PopupProperties
    import androidx.core.text.HtmlCompat
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import com.example.composewordgame1.AnagramsNoLengthGiven
    import com.example.composewordgame1.R
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.withContext

    data class Letters(val letters : String, var ndx :Int)
    data class LetterInfo(val letter:Char, val ndx:Int, var selected : Boolean =false)



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Woord1Screen(navController : NavController) {
        var aantalLetters by remember { mutableStateOf("0") }
        var aantalAnagrammen by remember { mutableStateOf("1") }
        var changed by remember { mutableStateOf(false) }
        var isCheckedForResult = remember { mutableStateOf( false) }
        var finalReqString by remember { mutableStateOf("empty") }
        val aantLtrs = remember { FocusRequester() }
        val aantAns = remember { FocusRequester() }

        var strs by remember { mutableStateOf(arrayListOf("")) }
        var showDet by remember { mutableStateOf(false) }
        var letsTo : MutableList<Letters> = ArrayList()
        var wrds by remember{ mutableStateOf(letsTo)}
        var woorden = ArrayList<String>()

        LaunchedEffect(finalReqString) {
            withContext(Dispatchers.IO) {
                if (!finalReqString.toString().equals("empty")) {
                    strs = myReq.MakeRequest(finalReqString)

                    showDet = true
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.WoordGame),
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        color = Color.Magenta
                    )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                OutlinedTextField(
                    value = aantalLetters,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = {
                        aantalLetters = it
                        changed =false
                        if (aantalLetters.length ==0 ) {
                            aantLtrs.captureFocus()
                        } else {
                            aantLtrs.freeFocus()
                        }
                    },
                    label = { Text(stringResource(R.string.aantalletters)) },
                    textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth(0.2f)
                        .focusRequester(aantLtrs)
                )

                OutlinedTextField(
                    value = aantalAnagrammen,
                    onValueChange = {
                        aantalAnagrammen = it
                        changed = false
                        if (aantalAnagrammen.length == 0 ) {
                            aantAns.captureFocus()
                        } else {
                            aantAns.freeFocus()
                        }

                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text(stringResource(R.string.aantalAnagrammen)) },
                    textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth(0.4f)
                        .focusRequester(aantAns)
                )
                Button(
                    onClick = {
                        changed = !changed
                        isCheckedForResult.value = false
                       // buttonHasFocus =true

                    },
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .align(Alignment.Bottom)
                ) {
                    Text(text = stringResource(R.string.speel))
                }
            }
///////////////////////////////////

            if (changed ) {
                var al = "0"
                if (aantalLetters == "") {
                    al = "0"
                } else al = aantalLetters
                var an = "0"
                if (aantalAnagrammen == "") {
                    an = "0"
                } else an = aantalAnagrammen
                // changed = !changed

                if(al.toInt() == 0){
                    AnagramsNoLengthGiven(an.toInt())
                }
                else {
                    ShowLetters(al.toInt(), an.toInt())
                }
                dataStorage.woorden.forEach {if(it.length > 1) woorden.add(it)}


                if ( dataStorage.woorden.isNullOrEmpty()) {

                    Popup(
                        alignment = Alignment.BottomCenter,
                        properties = PopupProperties(dismissOnClickOutside = true)
                    ) {
                        Text(
                            text = "\n\n\n\n\n\n"+ stringResource(R.string.erzijngeen) + aantalAnagrammen + " Anagrams " + "\n"  + stringResource(
                                                            R.string.voor) + aantalLetters + stringResource(
                                                                                            R.string.letters),
                            textDecoration = TextDecoration.Underline
                        )
                    }
                } else {
                   if (aantalAnagrammen == "") aantalAnagrammen = "0"

                    val ss1 = dataStorage.woorden
                    val aantletters = dataStorage.woorden[0].length

                    var i = 0
                    wrds.clear()
                    ss1.forEach() { it ->
                        if (it.length > 2) {
                            wrds.add(i, Letters(it.trim(), i))
                            i = i + 1
                        }
                    }


                    (1 .. aantalAnagrammen.toInt()).forEach() {
                            ShowAndSelectLetters(rnr = it, aantLetters = aantletters , Letters(wrds[it-1].letters,it))
                    }
///////
                    Checkbox(checked = isCheckedForResult.value, onCheckedChange = { isCheckedForResult.value = it })

                    if(isCheckedForResult.value){

                        Row(Modifier.padding(2.dp)) {
                            dataStorage.woorden.forEach {
                            Text(
                                text = it,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .clickable(onClick = {
                                        finalReqString = it
                                    }))

                            }
                        }

                    }
                    else {
                        showDet = false
                        finalReqString =""
                    }

                }

            }
            if (showDet && changed) {

                var s =""
                strs.forEach(){ s = s + it + '\n'}
                val spannedText = HtmlCompat.fromHtml(s, 0)
                //DetailPopup(strs[0])
                DetailPopup(spannedText, 250.dp)
            }

        }

    }










