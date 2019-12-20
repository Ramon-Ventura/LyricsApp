package com.example.lyricsapp.apiresponse

class Track(name: String, text: String, lang: Lang) {
    var name: String? = ""
    var text: String? = ""
    var lang: Lang? = null
    init {
        this.name = name
        this.text = text
        this.lang = lang
    }
}