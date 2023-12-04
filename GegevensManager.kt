package com.example.composewordgame1

import android.content.Context
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.util.zip.GZIPInputStream
import android.util.Log as Log1

public class  GegevensManager(cntx : Context)  {


    var cntx = cntx
    var languageFiles = arrayListOf("us", "nl", "fr","eng")
    var currentLanguage = "nl"
    var langList: MutableList<String> = ArrayList()
    lateinit var woordenMap : WoordKaart




    fun getWoordenVoor( language: String) {

        langList.clear()

        uitpakken(cntx.assets.open(language).buffered(), langList)
        woordenMap = WoordKaart(langList)

        var l = langList.size

    }

    fun uitpakken(fIStr: InputStream, woorden: MutableList<String>) {

        var bis = BufferedInputStream(fIStr)
        var gis = GZIPInputStream(bis)
        var bffrdr = BufferedReader(gis.bufferedReader(Charsets.UTF_8))
        woorden.addAll(bffrdr.readLines())

    }



}
