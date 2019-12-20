package com.example.lyricsapp.apiresponse

class Result(artist: Artist,track: Track, copyright: Copyright) {
    var artist: Artist? = null
    var track: Track? = null
    var copyright: Copyright? = null
    init {
        this.artist = artist
        this.track = track
        this.copyright = copyright
    }
}