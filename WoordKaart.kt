package com.example.composewordgame1

import android.util.Log
import io.ktor.http.HttpHeaders.If
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class AnagramData(val letters: String, val woorden:List<String>, val lengte: Int )
class Trio( var aantal: Int, var words : MutableList<String>, var letters : String)
class  Duo(var letters: String, var words: MutableList<String>)
class ByAantal ( var aantal : Int, var duo: Duo)

class WoordKaart(var anaList : List<String>) {

    lateinit var srtdCharArr: CharSequence
    lateinit var str: String

    val anaMap = mutableMapOf<Long, Trio>()


    init {
        anaList.forEach {
            val keyt: Long
            keyt = getkey(it)

            if (anaMap.containsKey(keyt)) {
                anaMap[keyt]?.aantal?.plus(1)
                var getal = anaMap[keyt]?.aantal
                if (getal != null) {
                    anaMap[keyt]?.aantal = getal + 1
                }

                var coll = arrayListOf(it)

                if (anaMap[keyt]?.words != null) {
                    anaMap[keyt]?.words?.addAll(coll)
                    // println("")
                    //  anaMap[keyt]?.words?.forEach(){print("woord is ${it} ")}
                }

                // anaMap[keyt]?.second?.toMutableList()?.plus(it)

            } else {
                var strList = arrayListOf(it)
                var sortedstr = it.toCharArray().sortedArray()
                var ss: String = ""
                sortedstr.forEach {
                    if (it.isLetterOrDigit()) {
                        ss = ss.plus(it)
                    }
                }
                var anaPair = Trio(1, strList, ss)
                anaMap[keyt] = anaPair


            }
        }


    }

    fun getStringsContaining(subs: String, minLen: Int): List<String> {
        var lst: MutableList<String> = ArrayList()

        anaMap.values.forEach() {
            if (it.letters.length >= minLen) {
                if (contains(it.letters, subs)) {
                    lst.addAll(it.words)
                }
            }

        }
        return lst

    }

    suspend fun getScrabbleMatches(pattern: String, contains: String): List<String> {
        var rE = pattern.toRegex()
        var matches: ArrayList<String> = arrayListOf()
        var minLen = 1

        var str = pattern.replace(".*", "").replace("^", "").replace("$", "")
        minLen = max(str.length, contains.length)
        getStringsContaining(contains, minLen).forEach {
            if (rE.containsMatchIn(it)) {
                matches.add(it)
                Log.d("scrabble", " added ${it}")

            }
        }

        return matches
    }

    private fun getkey(it1: String): Long {
        var k = it1.toCharArray()
        k.sort()
        var ke: Long
        ke = 1

        k.forEach { ke = ke * (it.hashCode() + 1999) % (Long.MAX_VALUE) }
        return ke
    }

    fun anasOfCount(i: Int): List<String> {
        var anal = mutableListOf("")
        anaMap.forEach() { it ->
            if (it.value.aantal == i) {
                anal.addAll(it.value.words)
            }
        }
        return anal

    }

    /*
    *  is s2 collection contained in s1 collection
    *
    * */
    fun contains(s1: String, s2: String): Boolean {
        if (s1.length < s2.length)
            return false

        var s1Arr: MutableList<Char> = arrayListOf()
        var s2Arr: MutableList<Char> = arrayListOf()
        var cntFound = 0

        s1.forEach {
            s1Arr.add(it)
        }
        s2.forEach { s2Arr.add(it) }
        var i = 0
        var j = 0
        s1Arr.sort()
        s2Arr.sort()

        while (i < s2Arr.size && j < s1Arr.size) {
            if (s2Arr[i] < s1Arr[j])
                return false
            if (s2Arr[i] == s1Arr[j]) {
                i = i + 1
                j = j + 1
                cntFound += 1
                if (cntFound == s2Arr.size)
                    return true
                continue
            }
            if (s2Arr[i] > s1Arr[j]) {
                j = j + 1
                continue
            }

        }
        if (cntFound == s2Arr.size)
            return true
        return false

    }

    fun contains2(s1: String, s2: String): Boolean {
        if (s1.length < s2.length)
            return false

        var s1Arr: MutableList<Char> = arrayListOf()
        var s2Arr: MutableList<Char> = arrayListOf()

        s1.forEach {
            s1Arr.add(it)
        }
        s2.forEach { s2Arr.add(it) }
        var i = 0
        var j = 0
        s1Arr.sort()
        s2Arr.sort()

        while (i < s2Arr.size && j < s1Arr.size) {
            if (s2Arr[i] < s1Arr[j])
                return false
            if (s2Arr[i] == s1Arr[j]) {
                i = i + 1
                j = j + 1
                continue
            }
            if (s2Arr[i] > s1Arr[j]) {
                j = j + 1
                continue
            }

        }
        if (i == s2Arr.size)
            return true
        return false

    }

    fun findLetters(aantal: Int): Trio {
        lateinit var gevonden: Trio
        var aant = aantal
        var lst: MutableList<Trio> = ArrayList()

        anaMap.values.forEach() {
            if (it.aantal == aant) {
                lst.add(it)
            }

        }
        // take a random item
        var lstlen = lst.size


        var rndnr = Random.nextInt().absoluteValue % lstlen

        return lst[rndnr]


    }

    fun findWordsWithExtraChars(baseString: CharArray, extraAantal: Int): ArrayList<String> {
        var lst: MutableList<String> = ArrayList()
        var len2Lookfor = baseString.size + extraAantal
        var bstr = String(baseString)

        anaMap.values.forEach {

            if (it.letters.length == len2Lookfor) {

                if (contains2(it.letters, bstr)) {

                    lst.addAll(it.words)


                }

            }
        }
        return lst as ArrayList<String>
    }
    fun findAllWordsContaining(baseString: CharArray): ArrayList<String> {
        var lst: MutableList<String> = ArrayList()
        var bstr = String(baseString)

        anaMap.values.forEach {

            if (contains2(it.letters, bstr)) {

                lst.addAll(it.words)


            }

        }
        return lst as ArrayList<String>
    }


    fun FindWordsWithPattern(
        baseString: CharArray,
        extraAantal: Int,
        patroon: String,
        findAll: Boolean
    ): ArrayList<String>
    {
        var startList = when(findAll){
                          true ->  findAllWordsContaining(baseString)
                        else -> findWordsWithExtraChars(baseString, extraAantal)
                     }

        var endList: MutableList<String> = ArrayList<String>()
        var b1 = patroon.isEmpty()
        var b2 = patroon.equals(".*")

        if (b1 or b2) {
            return startList
        }

        val rExp = patroon.toRegex()
        startList.forEach {
            if (rExp.containsMatchIn(it)) {
                endList.add(it)
            }
        }

        return endList as ArrayList<String>

    }

    fun findAnagrams(aantWoorden: Int): List<AnagramData> {
        val anaList: List<AnagramData> = ArrayList()
        return findAllAnagramsOfAantal(aantWoorden)
    }

    fun findAllAnagramsOfAantal(aantal: Int): List<AnagramData> {
        val builder = StringBuilder()
        val lstAna: MutableList<AnagramData> = ArrayList()

        anaMap.forEach() {

            if (it.value.aantal == aantal) {
                lstAna.add(AnagramData(it.value.letters, it.value.words, it.value.aantal))
            }

        }
        return lstAna.sortedBy { it.letters }
    }

    fun findAllAnagramsOfAantalLengte(aantal: Int, lengte: Int): List<AnagramData> {

        val lstAna: MutableList<AnagramData> = ArrayList()

        anaMap.forEach() {

            if ((aantal == 0 && lengte == 0) ||
                (it.value.aantal == aantal && it.value.letters.length == lengte)
            ) {

                lstAna.add(AnagramData(it.value.letters, it.value.words, it.value.aantal))

            }


        }
        return lstAna
    }
        fun reset(anaList: List<String>) {

            anaMap.clear()
            anaList.forEach {
                val keyt: Long
                keyt = getkey(it)

                if (anaMap.containsKey(keyt)) {
                    anaMap[keyt]?.aantal?.plus(1)
                    var getal = anaMap[keyt]?.aantal
                    if (getal != null) {
                        anaMap[keyt]?.aantal = getal + 1
                    }

                    var coll = arrayListOf(it)

                    if (anaMap[keyt]?.words != null) {
                        anaMap[keyt]?.words?.addAll(coll)
                        // println("")
                        //  anaMap[keyt]?.words?.forEach(){print("woord is ${it} ")}
                    }

                    // anaMap[keyt]?.second?.toMutableList()?.plus(it)

                } else {
                    var strList = arrayListOf(it)
                    var sortedstr = it.toCharArray().sortedArray()
                    var ss: String = ""
                    sortedstr.forEach {
                        if (it.isLetterOrDigit()) {
                            ss = ss.plus(it)
                        }
                    }
                    var anaPair = Trio(1, strList, ss)
                    anaMap[keyt] = anaPair


                }


            }
        }
    fun getRandomStringOfLength(len : Int) : String{
        val wrdsOfLength : MutableList<String> = ArrayList()

        anaMap.values.forEach(){it -> if(it.letters.length == len)
                                 wrdsOfLength.add(it.letters) }

        return wrdsOfLength.random()
    }

    fun getFomLetterAndExtra( letters:String, extra: Int): List<String>{
        val woordenLijst : MutableList<String> = ArrayList()
        val key = getkey(letters )
        val baseLength = letters.length
        // first get all words belonging to the key
        anaMap[key]?.words?.forEach(){it -> woordenLijst.add(it)}

        if(extra > 0){
            anaMap.values.forEach(){it -> if(it.letters.length in baseLength+1.. baseLength + extra &&
                                              contains(it.letters,letters)                         ) {
                              woordenLijst.addAll(it.words)
                 }
            }
        }
        return woordenLijst
    }


}


