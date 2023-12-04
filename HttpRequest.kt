package com.example.composewordgame1.screens

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MyHttpRequest( val baseAddress: String) {

    var country: CurrentLocale = CurrentLocale.DUTCH

    val client = HttpClient()

    fun setLocale( cntry: CurrentLocale){
        country = cntry
    }

    suspend fun MakeRequest(req: String): ArrayList<String>{
       return when(country){
           CurrentLocale.DUTCH ->  makeRequest(req)
           CurrentLocale.ENGLISH -> makeEnglishRequest( req)
           CurrentLocale.FRENCH -> makeFrenchRequest( req)
           CurrentLocale.GERMAN -> makeGermanRequest ( req )
        }

    }
    suspend fun makeFrenchRequest( req: String): ArrayList<String>{
        var words : ArrayList<String> = arrayListOf(" ")
        //var reqString = baseAddress + "/"  + "?q=" + req
       // var reqString = "https://www.le-dictionnaire.com/definition" + "/" + req
        var reqString = "https://www.cnrtl.fr/definition" + "/" + req

        var response : HttpResponse = client.get(reqString)
        var responsAsSeq = response.readBytes().decodeToString()

        val document = Jsoup.parse(responsAsSeq)


        try {
           // document.getElementsByClass("defbox").eachText().forEach(){
            document.getElementsByClass("tlf_cdefinition").eachText().forEach(){
                words.add(it + '\n')
            }

        }
        finally{

            return words
        }


    }

    suspend fun makeGermanRequest( req: String): ArrayList<String>{
        var words : ArrayList<String> = arrayListOf(" ")
        //var reqString = baseAddress + "/"  + "?q=" + req
        var reqString = "https://www.dwds.de/wb" + "/" + req
        // val response : HttpResponse = client.get("https://www.ensie.nl/betekenis/televisie?q=televisie"){
        var response : HttpResponse = client.get(reqString)
        var responsAsSeq = response.readBytes().decodeToString()

        val document = Jsoup.parse(responsAsSeq)

        var results =  ""
        try {
            //var els = document.getElementsByAttributeValue("div", "bedeutungsuebersicht")
            var bedeutung = document.getElementsByClass("dwdswb-definitionen")
            var es =document.select("div.bedeutungsuebersicht").select("li")

            var elsplus = document.select("span.dwdswb-belegtext")
            var elsplus2 = document.select("a.intern")

            if(es != null) {
                es.forEach() {
                    words.add(it.text() + "\n")
                }
            }

            if(bedeutung != null) {
                bedeutung.forEach() {
                    words.add(it.text())
                }
            }
            if(elsplus2 != null) {
                elsplus2.forEach() {
                    words.add(it.toString())
                }
            }

            if(elsplus != null) {
                elsplus.forEach() {
                    words.add(it.toString())
                }
            }


        }
        finally{

            if ( results.isNullOrEmpty()){

            }
            else {
                words.add(results)
            }
            return words
        }




        /*     document.getElementsByClass("def").eachText().forEach(){
                 words.add(it + '\n')
             }
     */


    }
    suspend fun makeEnglishRequest( req: String): ArrayList<String>{
        var words : ArrayList<String> = arrayListOf(" ")
        //var reqString = baseAddress + "/"  + "?q=" + req
        var reqString = "https://www.merriam-webster.com/dictionary" + "/" + req
        // val response : HttpResponse = client.get("https://www.ensie.nl/betekenis/televisie?q=televisie"){
        var response : HttpResponse = client.get(reqString)
        var responsAsSeq = response.readBytes().decodeToString()

        val document = Jsoup.parse(responsAsSeq)

        var results =  ""
        try {
            results = document.select("meta[name=description]").get(0).attr("content")


        }
       finally{

           if ( results.isNullOrEmpty()){
               words[0]= "not in the dictionary"

           }
           else {
               words.add(results)
           }
           return words
        }




        /*     document.getElementsByClass("def").eachText().forEach(){
                 words.add(it + '\n')
             }
     */



    }

    suspend fun makeRequest( req: String): ArrayList<String>{
        var words : ArrayList<String> = ArrayList()
        var reqString = "https://www.ensie.nl/betekenis" + "/"  + "?q=" + req

        var response : HttpResponse = client.get(reqString)
        var responsAsSeq = response.readBytes().decodeToString()

        val document = Jsoup.parse(responsAsSeq)

        document.getElementsByClass("def").eachText().forEach(){
            words.add(it + '\n')
        }
        return words


    }

    suspend fun makeWikiRequest( req: String): ArrayList<String>{
        var words : ArrayList<String> = ArrayList()
        var reqString = "https://nl.wikipedia.org/wiki" +"/" + req
        // val response : HttpResponse = client.get("https://www.ensie.nl/betekenis/televisie?q=televisie"){
        var response : HttpResponse = client.get(reqString)
        var responsAsSeq = response.readBytes().decodeToString()

        val document = Jsoup.parse(responsAsSeq)


        val elementen = document.select("p")
        elementen.forEach(){ words.add(it.toString())}
     //   document.getElementsByClass("mw-editsection-bracket").eachText().forEach(){
       //     words.add(it + '\n')
       // }

        return words


    }


}