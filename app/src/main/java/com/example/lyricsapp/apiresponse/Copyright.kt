package com.example.lyricsapp.apiresponse

class Copyright(notice: String, artist: String, text: String) {
    var notice: String? = ""
    var artist: String? = ""
    var text: String? = ""
    init {
        this.notice = notice
        this.artist = artist
        this.text = text
    }
}