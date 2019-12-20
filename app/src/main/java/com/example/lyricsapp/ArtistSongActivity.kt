@file:Suppress("DEPRECATION")

package com.example.lyricsapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lyricsapp.apiresponse.ApiResponse
import com.google.gson.Gson

class ArtistSongActivity : AppCompatActivity() {
    private lateinit var btnLyric: Button
    private lateinit var etArtist: EditText
    private lateinit var etTrack: EditText
    private lateinit var  progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_song)

        //init
        btnLyric = findViewById(R.id.buttonSearch)
        etArtist = findViewById(R.id.editTextArtist)
        etTrack = findViewById(R.id.editTextTrack)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Searching Lyric...")

        //Action bar and title
        supportActionBar?.title = "Search Lyric"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Search button listener
        btnLyric.setOnClickListener {
            if(etArtist.length()==0 || etTrack.length()==0){
                showDialogEmpty()
            }else{
                showDialog()
            }
        }
    }

    private fun verifyAndConnect(artist : String, track : String) {
        if (Network.verifyConnection(this)) {
            progressDialog.show()
            httpVolley(getUrlApi(artist,track))
        } else {
            progressDialog.dismiss()
            Toast.makeText(this, "¡No internet connection!", Toast.LENGTH_SHORT).show()
        }
    }

    //Get url api
    private fun getUrlApi(artist : String, track : String) : String{
        return "https://api.lyrics.ovh/v1/$artist/$track"
    }

    private fun httpVolley(url: String) {
        // Instanciar la cola de peticiones
        val queue = Volley.newRequestQueue(this)

        // Obtener un string de respuesta desde la URL enviada
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("HTTPVolley",  response)
                jsonToObject(response)
            },
            Response.ErrorListener {
                progressDialog.dismiss()
                Log.d("HTTPVolley", "Error in URL $url")
                showDialogNull()
            })

        // Agregar la peticion a la cola de peticiones
        queue.add(stringRequest)
    }

    private fun jsonToObject(response: String) {
        // Inicializar los valores de tipo Gson
        val gson = Gson()
        val apiResponse = gson.fromJson(response, ApiResponse::class.java)
        if(apiResponse.lyrics!=null){
            val intent = Intent(this,LyricActivity::class.java)
            intent.putExtra("Lyric",apiResponse.lyrics)
            intent.putExtra("Artist",etArtist.text.toString().trim())
            intent.putExtra("Song",etTrack.text.toString().trim())
            progressDialog.dismiss()
            startActivity(intent)
        }else{
            showDialogNull()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("CONFIRM")
        //LinearLayout
        val linearLayout = LinearLayout(this)
        linearLayout.orientation=LinearLayout.VERTICAL

        //Views to dialog
        val artist = TextView(this)
        artist.setTextSize(TypedValue.COMPLEX_UNIT_SP,20.0f)
        artist.text = getString(R.string.dialog_text_view_artist,etArtist.text.toString().trim())
        val song = TextView(this)
        song.setTextSize(TypedValue.COMPLEX_UNIT_SP,20.0f)
        song.text = getString(R.string.dialog_text_view_song,etTrack.text.toString().trim())
        linearLayout.addView(artist)
        linearLayout.addView(song)
        linearLayout.setPadding(10,10,10,10)

        builder.setView(linearLayout)

        //Buttons recover
        builder.setPositiveButton("Continue"){ _, _ ->
            verifyAndConnect(etArtist.text.toString().trim(),etTrack.text.toString().trim())
        }
        //Button Recover
        builder.setNegativeButton("Cancel"){ dialog, _ ->
            dialog.dismiss()
        }
        //Show dialog
        builder.create().show()
    }

    private fun showDialogEmpty(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("DATA MISSING!")
        //LinearLayout
        val linearLayout = LinearLayout(this)

        //Views to dialog
        val alert = TextView(this)
        alert.setTextSize(TypedValue.COMPLEX_UNIT_SP,20.0f)
        alert.text = getString(R.string.alert_text_empty)
        linearLayout.addView(alert)
        linearLayout.setPadding(10,10,10,10)
        builder.setView(linearLayout)

        //Continue button
        builder.setPositiveButton("Continue"){ dialog, _ ->
            dialog.dismiss()
        }
        //Show dialog
        builder.create().show()
    }

    private fun showDialogNull(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("OPPS :(")
        //LinearLayout
        val linearLayout = LinearLayout(this)

        //Views to dialog
        val alert = TextView(this)
        alert.setTextSize(TypedValue.COMPLEX_UNIT_SP,20.0f)
        alert.text = getString(R.string.alert_text_null)
        linearLayout.addView(alert)
        linearLayout.setPadding(10,10,10,10)
        builder.setView(linearLayout)

        //Continue button
        builder.setPositiveButton("Continue"){ dialog, _ ->
            dialog.dismiss()
        }
        //Show dialog
        builder.create().show()
    }
}
