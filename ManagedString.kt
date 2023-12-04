package com.example.composewordgame1

import androidx.compose.runtime.*

data class CharNdx(val ch: Char, val ndx: Int)


class ManagedString(inStr : String ) {
    public val origString : String = inStr
    public lateinit var placeHolder : ArrayList<CharNdx>
    public lateinit var sortedStr : ArrayList<CharNdx>

    init{
        var carray  = origString.toCharArray()
        carray.forEachIndexed(){ i,c -> sortedStr[i]=CharNdx(c,i)}
        sortedStr.sortBy{it -> it.ch }
        (1..inStr.length).forEach(){ it -> placeHolder[it-1] = CharNdx('_',it-1)}

    }
    @Composable
    fun GetPlaceholderString(): ArrayList<CharNdx>{
        var orig by remember {mutableStateOf(placeHolder)}
        return orig
    }
}