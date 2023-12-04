package com.example.composewordgame1.screens

import android.text.Spanned
import android.widget.TextView
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.google.android.material.textview.MaterialTextView


@Composable
fun DetailPopup( toShow : Spanned, sze : Dp  = 160.dp){
    var popupControl by remember { mutableStateOf(true) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sze)
                    .border(BorderStroke(1.dp, Color.LightGray))
            ) {
                // Composable content to be shown in the Popup
                TextButton(onClick = {
                    popupControl = !popupControl

                }) {
                    if (popupControl) {
                        Text(
                            toShow.toString().replace("Startseite", "ZZ")
                                .replace("Wörterbuch", "ZZ"),
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        )
                    } else {
                        Text(
                            "click for info "
                        )
                    }
                }


        }
}

@Composable
fun DetailPopup2( theword: String,toShow : Spanned, sze : Dp  = 160.dp, popupcontrol : Boolean, closePopUp : () -> Unit){
    var popupControl by remember { mutableStateOf(true) }
    var openup by remember{mutableStateOf(true)}
    popupControl = popupcontrol
    if(popupControl && !theword.isNullOrEmpty() && theword.length>2) {
        AlertDialog(
            onDismissRequest = {
                openup = true
            },
            title = {
                Text(text = theword)
            },
            text = {
                Text(toShow.toString(),maxLines=40,modifier=Modifier.verticalScroll(state= rememberScrollState(), enabled=true))
            },
            confirmButton = {

            },
            dismissButton = {
                Button(

                    onClick = closePopUp) {
                    Text("Dismiss Button")
                }
            }
        )
    }


}
