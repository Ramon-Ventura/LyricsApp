@file:Suppress("DEPRECATION")

package com.example.lyricsapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lyricsapp.apiresponse.ApiResponse
import com.example.lyricsapp.interfaces.ClickListener
import com.example.lyricsapp.recycledata.Popular
import com.example.lyricsapp.recycledata.PopularAdapter
import com.google.gson.Gson

class PopularSongsActivity : AppCompatActivity() {
    private lateinit var gArtist: String
    private  lateinit var  gSong: String

    var populares: ArrayList<Popular>? = null

    var mylist: RecyclerView? = null
    var layoutManager:RecyclerView.LayoutManager? = null
    var adapter: PopularAdapter? = null
    private lateinit var  progressDialog : ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_songs)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Searching Lyric...")

        //Action bar and title
        supportActionBar?.title = "Popular Songs"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Populars List
        populares = ArrayList()
        populares?.add(Popular(R.drawable.ride_the_lightning,"Metallica","Ride the Lightning","Fade to black"))
        populares?.add(Popular(R.drawable.master_of_puppets,"Metallica","Master of Puppets","Master of Puppets"))
        populares?.add(Popular(R.drawable.metallica,"Metallica","Metallica","Enter Sandman"))
        populares?.add(Popular(R.drawable.fear_of_the_dark,"Iron Maiden","Fear of the Dark","Fear of the Dark"))
        populares?.add(Popular(R.drawable.a_night_at_the_opera,"Queen","A Night At The Opera","Bohemian Rhapsody"))

        mylist = findViewById(R.id.lista)
        layoutManager = LinearLayoutManager(this)
        adapter = PopularAdapter(populares!!,object :ClickListener{
            override fun onClick(view: View, position: Int) {
                gArtist = populares?.get(position)?.artist!!
                gSong = populares?.get(position)?.song!!
                verifyAndConnect(gArtist,gSong)
            }
        })
        mylist?.layoutManager = layoutManager
        mylist?.adapter = adapter

    }

    private fun verifyAndConnect(artist : String, track : String) {
        if (Network.verifyConnection(this)) {
            progressDialog.show()
            httpVolley(getUrlApi(artist,track))
        } else {
            progressDialog.dismiss()
            Toast.makeText(this, "¡No tienes conexión a Internet!", Toast.LENGTH_SHORT).show()
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
                Log.d("HTTPVolley", "Error en la URL $url")
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
            intent.putExtra("Artist",gArtist)
            intent.putExtra("Song",gSong)
            progressDialog.dismiss()
            startActivity(intent)
        }else{
            showDialogNull()
        }

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
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
