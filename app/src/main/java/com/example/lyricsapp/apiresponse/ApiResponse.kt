package com.example.lyricsapp.apiresponse

class ApiResponse (success: Boolean, result: Result, microDuration: Int, artist: String, song: String) {
    var success: Boolean? = null
    var result: Result? = null
    var microDuration: Int? = 0
    var artist: String? = ""
    var song: String? = ""

    //Initialize properties
    init {
        this.success = success
        this.result = result
        this.microDuration = microDuration
        this.artist = artist
        this.song = song
    }
}