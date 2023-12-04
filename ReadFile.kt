package com.example.composewordgame1.screens

import android.content.Context
import android.util.Log
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.example.composewordgame1.WoordKaart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.util.zip.GZIPInputStream

object dataStorage{
    val WK = WoordKaart(listOf("") )
    var bGeladen = false
    var woorden : MutableList<String> = arrayListOf()

    fun setWrden(wrden: MutableList<String>){
        woorden = wrden
    }

}
/*
@Composable
fun  ReadCompressedFile( fName: String ){
    val context = LocalContext.current
    var woorden : MutableList<String> = ArrayList()

        var istr = context.assets.open(fName).buffered()
        var bis = BufferedInputStream(istr)
        val size = istr.available()
        var gis = GZIPInputStream(bis)
        var bffrdr = BufferedReader(gis.bufferedReader(Charsets.UTF_8))
        woorden.addAll(bffrdr.readLines())
        dataStorage.WK.reset(woorden)
        dataStorage.bGeladen = true

}*/

suspend fun readCompressedFile( istr : BufferedInputStream) {
    withContext(Dispatchers.IO) {
        var woorden: MutableList<String> = ArrayList()
        var succeeded = false
        //var istr = context.assets.open(fName).buffered()
        var bis = BufferedInputStream(istr)
        val size = istr.available()
        var gis = GZIPInputStream(bis)
        var bffrdr = BufferedReader(gis.bufferedReader(Charsets.UTF_8))
        woorden.addAll(bffrdr.readLines())//.filter { it == "CANADA" })
        dataStorage.WK.reset(woorden)
        dataStorage.bGeladen = true
    }
}
