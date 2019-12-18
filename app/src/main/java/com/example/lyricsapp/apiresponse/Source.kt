package com.example.lyricsapp.apiresponse

class Source(name: String, homePage: String, url: String) {
    var name: String? = ""
    var homePage: String? = ""
    var url: String? = ""
    //Initialize variables
    init {
        this.name = name
        this.homePage = homePage
        this.url = url
    }
}