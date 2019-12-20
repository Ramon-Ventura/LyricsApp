package com.example.lyricsapp.recycledata

class Popular(photo:Int, artist: String, album: String, song: String) {
    var artist: String = ""
    var album: String = ""
    var song: String = ""
    var photo: Int = 0
    init {
        this.artist = artist
        this.album = album
        this.song = song
        this.photo = photo
    }
}