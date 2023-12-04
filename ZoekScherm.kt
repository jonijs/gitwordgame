    package com.example.composewordgame1.screens


    import android.content.Intent
    import android.net.Uri
    import android.util.Log
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.material.Button
    import androidx.compose.material.TextField
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.Text
    import androidx.compose.runtime.*
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.text.input.TextFieldValue
    import androidx.compose.ui.text.toUpperCase
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import com.example.composewordgame1.R
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.withContext

    @Composable()
    fun ZoekScherm(navController: NavController){
        var woordlengte by rememberSaveable { mutableStateOf("0") }
        var bevatLetters by rememberSaveable { mutableStateOf("abc") }
        var zoekpatroon by remember{ mutableStateOf(".*")}
        var zoekresultaat by rememberSaveable { mutableStateOf(arrayListOf(""))}

        var gevonden by remember { mutableStateOf(false)}
        var opnieuw by remember { mutableStateOf(false)}
        var somethingChanged by remember { mutableStateOf(false)}


      Column(modifier = Modifier
          .fillMaxWidth(1f)
          .padding(10.dp),
             horizontalAlignment = Alignment.Start , verticalArrangement = Arrangement.Center) {
          Spacer(modifier=Modifier.size(height= 10.dp,width=200.dp))

          Row(
              modifier = Modifier.fillMaxWidth(1f),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
          ) {
               OutlinedTextField(
                  value = woordlengte,
                  onValueChange = { woordlengte = it
                      somethingChanged = true
                                  },
                  keyboardOptions = KeyboardOptions(
                      keyboardType = KeyboardType.Number
                  ),
                  label = { Text(text = stringResource(R.string.woordLengte)) },
                  textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                  modifier = Modifier.fillMaxWidth(0.3f)
              )

              val a2 = OutlinedTextField(
                  value = bevatLetters,
                  onValueChange = { bevatLetters = it
                                    somethingChanged = true},
                  keyboardOptions = KeyboardOptions(
                      keyboardType = KeyboardType.Ascii
                  ),
                  label = { Text(text = stringResource(R.string.bevatLetters)) },
                  textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                  modifier = Modifier.fillMaxWidth(0.7f)
              )

              //Button(onClick= dataStorage
          }
          Row(
              modifier = Modifier.fillMaxWidth(1f),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
          ) {
              OutlinedTextField(value = zoekpatroon,
                  onValueChange = { zoekpatroon = it },
                  keyboardOptions = KeyboardOptions(
                      keyboardType = KeyboardType.Ascii
                  ),

                  label = { Text(text = stringResource(R.string.zoekpatroon)) }
              )
          }
          Row(
              modifier = Modifier.fillMaxWidth(1f),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
          ) {
              Button(
                  onClick = {

                      gevonden = !gevonden

                      zoekresultaat.removeAll(zoekresultaat.toSet())
                      zoekresultaat.addAll(

                          dataStorage.WK.FindWordsWithPattern(
                              bevatLetters.toUpperCase().toCharArray(),

                              woordlengte.toInt() - bevatLetters.length,
                              zoekpatroon.toUpperCase(),
                              woordlengte.equals("0")

                          )

                      )
                  }, modifier = Modifier
                      .height(IntrinsicSize.Max)
                      .padding(10.dp)
              ) {
                  Text(
                      text = if (!gevonden) stringResource(R.string.Zoek)
                      else stringResource(R.string.opnieuw),
                      color= Color.White
                  )
              }
          }

          if (gevonden ){
              ZoekResultaten(zoekresultaat, navController)
              opnieuw = true
          }
          if ( opnieuw && !gevonden ){
              ZoekResultaten(zoekresultaat, navController)
              opnieuw = false
          }
      }

    }

    @Composable
    fun openURL() {

        // in the below line, we are creating
        // variables for URL
        val url = remember {
            mutableStateOf(TextFieldValue())
        }

        // on below line we are creating
        // a variable for a context
        val ctx = LocalContext.current

        // on below line we are creating a column
        Column(
            // on the below line we are specifying modifier
            // and setting max height and max width
            // for our column
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth()
                // on below line we are
                // adding padding for our column
                .padding(5.dp),
            // on below line we are specifying horizontal
            // and vertical alignment for our column
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // in the below line, we are creating a text field
            // for getting URL from user.
            TextField(
                // on below line we are specifying
                // value for our  text field.
                value = url.value,

                // on the below line we are adding on
                // value change for text field.
                onValueChange = { url.value = it },

                // on below line we are adding place holder as text
                placeholder = { Text(text = "Enter your URL") },

                // on the below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                // on the below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

                // on below line we are
                // adding single line to it.
                singleLine = true,
            )

            // on below line adding a spacer.
            Spacer(modifier = Modifier.height(20.dp))

            // on below line adding a button to open URL
            Button(onClick = {
                Log.e("tag","URL IS "+url.value.text)
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www." + url.value.text)
                )
                ctx.startActivity(urlIntent)
            }) {
                // on below line creating a text for our button.
                Text(
                    // on below line adding a text ,
                    // padding, color and font size.
                    text = "Open URL",
                    modifier = Modifier.padding(10.dp),
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
    @Preview
    @Composable()
    fun PreviewZoekScherm(){
        ZoekScherm(navController = rememberNavController())
    }