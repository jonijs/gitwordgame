    package com.example.composewordgame1.screens

    import android.app.LocaleManager
    import android.content.Context.LOCALE_SERVICE
    import android.os.Build
    import android.os.LocaleList
    import androidx.annotation.RequiresApi
    import androidx.compose.foundation.border
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row


    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.selection.selectable
    import androidx.compose.foundation.selection.selectableGroup
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.Button
    import androidx.compose.material.Text
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.RadioButton
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.platform.LocalUriHandler
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavController
    import com.example.composewordgame1.R
    import java.util.*
    import kotlin.collections.ArrayList
    import kotlin.concurrent.thread

    data class ItemData(val image:Int, val txt : String)


    var imageList : MutableList<ItemData > = ArrayList( )
    fun createList():  MutableList<ItemData >{
        imageList.add(ItemData(R.drawable.nl, "nl"))
        imageList.add(ItemData(R.drawable.img_4, "de"))
        imageList.add(ItemData(R.drawable.img_3, "eng"))
        imageList.add(ItemData(R.drawable.img_2, "fr"))
        return imageList

    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  //  @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PickLanguage(navController: NavController) {

            val context = LocalContext.current

            val localeManager = context.getSystemService(LOCALE_SERVICE) as LocaleManager
            val currentLocale = localeManager.applicationLocales.toLanguageTags()
            var round = 0
            var buttonEnabled by remember { mutableStateOf(false) }

            val selected = remember {
                mutableStateOf("nl")
            }
            var l = { s: String -> {} }
            val imagelist = createList()
        Box(modifier=Modifier.padding(horizontal=50.dp, vertical = 200.dp)){
            Row(){
                Column(

                    modifier = Modifier
                        //.verticalScroll(state = rememberScrollState())
                        .fillMaxWidth(0.5f)
                        .selectableGroup()

                ) {

                    //  CompositionLocalProvider() {
                    CountryRow(selected, R.drawable.nl, "nl")
                    CountryRow(selected, R.drawable.img_2, "fr")
                    CountryRow(selected, R.drawable.img_3, "eng")
                    CountryRow(selected, R.drawable.img_4, "de")
                    when (selected.value) {
                        "nl" -> myReq.country = CurrentLocale.DUTCH
                        "de" -> myReq.country = CurrentLocale.GERMAN
                        "eng" -> myReq.country = CurrentLocale.ENGLISH
                        "fr" -> myReq.country = CurrentLocale.FRENCH
                    }
                }

                Column(modifier = Modifier.fillMaxWidth(1f).align(Alignment.CenterVertically)) {
                    //val localUriHandler = LocalUriHandler.current
                    Button(modifier = Modifier.fillMaxWidth(1.0f),
                        enabled = buttonEnabled,
                        onClick = {
                            navController.navigate("HoofdScherm")
                            localeManager.applicationLocales =
                                LocaleList(Locale(selected.value))


                           // localUriHandler.openUri("https://www.google.com")

                        }) {
                        Text(
                            text = when (selected.value.toString()) {
                                "nl" -> "Nederlands"
                                "de" -> "Deutsch"
                                "eng" -> "English"
                                "fr" -> "Francais"

                                else -> {
                                    "????"
                                }
                            }
                        )


                    }
                }
            }
        }


           LaunchedEffect(selected.value) {
               buttonEnabled = false
                readCompressedFile(context.assets.open(selected.value).buffered())
               when(selected.value){
                   "nl" -> myReq.country=CurrentLocale.DUTCH
                   "de" -> myReq.country=CurrentLocale.GERMAN
                   "eng" -> myReq.country=CurrentLocale.ENGLISH
                   "fr" -> myReq.country=CurrentLocale.FRENCH
               }
               buttonEnabled = true

           }
    }









