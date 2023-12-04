package com.example.composewordgame1.screens

import android.annotation.SuppressLint
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.*
import androidx.compose.material.DrawerValue.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.core.text.toHtml
import androidx.core.view.ViewCompat
import androidx.navigation.NavHostController


import com.example.composewordgame1.R
import com.example.composewordgame1.ui.theme.HtmlText
import kotlinx.coroutines.CoroutineScope

@Composable
fun Html(text: String) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
        }
    })
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HoofdScherm(navController: NavHostController) {
    val scaffoldState : ScaffoldState = rememberScaffoldState( )
    val rememberCoroutineScope : CoroutineScope = rememberCoroutineScope()
    var nxt by remember{ mutableStateOf(true)}
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
                    TopAppBar {

                        Text("Apropos")
                        Button(onClick = { nxt = !nxt },modifier=Modifier) {
                            if( nxt)
                                Text("<")
                            else Text( ">")

                        }
                    }
                 },

                    ////
                    content = {
                Surface(color = Color.Yellow, modifier = Modifier.fillMaxSize()) {

                // val str = HtmlCompat.fromHtml(stringResource(R.string.Uitleg),0)
                //

                        val tex =
                            LocalContext.current.resources.getText(R.string.Uitleg) //stringResource(R.string.Uitleg)
                        val tex2 =
                            LocalContext.current.resources.getText(R.string.Uitleg2) //stringResource(R.string.Uitleg)

                        val scrollState1 = rememberScrollState()

                        Column(
                            modifier = Modifier
                                .width(600.dp)
                                .padding(2.dp)
                                .border(2.dp, Color.Red) //for visual effect only
                                .verticalScroll(rememberScrollState())
                                .height(1500.dp)

                        ) {
                            AndroidView(
                                { context ->
                                    TextView(context).apply { text = tex }
                                },
                                Modifier
                                    .background(color = Color.Yellow)
                                    .padding(horizontal = 8.dp)

                            )

                        }

            }

        },

        bottomBar = {BottomNavigationBar(navController)}
    )

}












