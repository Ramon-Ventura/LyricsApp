package com.example.lyricsapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lyricsapp.apiresponse.ApiResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnLyric: Button
    private lateinit var etArtist: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init
        btnLyric = findViewById(R.id.buttonSearch)
        etArtist = findViewById(R.id.editTextArtist)

        btnLyric.setOnClickListener {
            verifyAndConnect(etArtist.toString().trim(),editTextTrack.text.toString().trim())
        }
    }

    private fun verifyAndConnect(artist : String, track : String) {
        if (Network.verifyConnection(this)) {
            httpVolley(getUrlApi(artist,track))
        } else {
            Toast.makeText(this, "¡No tienes conexión a Internet!", Toast.LENGTH_SHORT).show()
        }
    }

    //Get url api
    private fun getUrlApi(artist : String, track : String) : String{
        return "https://mourits.xyz:2096/?a=$artist&s=$track"
    }

    private fun httpVolley(url: String) {
        // Instanciar la cola de peticiones
        val queue = Volley.newRequestQueue(this)

        // Obtener un string de respuesta desde la URL enviada
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("HTTPVolley",  response)
                Toast.makeText(this, "Conexión establecida", Toast.LENGTH_LONG).show()
                jsonToObject(response)
            },
            Response.ErrorListener {
                Log.d("HTTPVolley", "Error en la URL $url")
                Toast.makeText(this, "¡Ha ocurrido un error en la conexión!", Toast.LENGTH_SHORT).show()
            })

        // Agregar la peticion a la cola de peticiones
        queue.add(stringRequest)
    }

    private fun jsonToObject(response: String) {
        // Inicializar los valores de tipo Gson
        val gson = Gson()
        val apiResponse = gson.fromJson(response, ApiResponse::class.java)
        textViewLyric.text = apiResponse.result?.lyrics.toString()
    }
}
