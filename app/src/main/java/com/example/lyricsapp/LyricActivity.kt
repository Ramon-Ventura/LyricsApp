package com.example.lyricsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lyric.*

class LyricActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyric)

        //Action bar and title
        supportActionBar?.title = "Lyric"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Intent gets information from previous activity (ArtistSongActivity)
        val objectIntent : Intent = intent
        val lyric = objectIntent.getStringExtra("Lyric")
        val artist = objectIntent.getStringExtra("Artist")
        val song = objectIntent.getStringExtra("Song")
        textViewInfo.text = getString(R.string.text_view_info_lyric,artist.toString(),song.toString())
        textViewLyric.text = lyric.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
