package com.example.lyricsapp.apiresponse

class Result(lyrics: String, source: Source) {
    var lyrics: String? = ""
    var source: Source? = null
    //Initialize variable
    init {
        this.lyrics = lyrics
        this.source = source
    }
}