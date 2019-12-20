package com.example.lyricsapp.recycledata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lyricsapp.R
import com.example.lyricsapp.interfaces.ClickListener
import kotlinx.android.synthetic.main.template.view.*

class PopularAdapter (items: ArrayList<Popular>,var clickListener: ClickListener): RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    var items: ArrayList<Popular>? = null
    var viewHolder: ViewHolder? = null
    init {
        this.items = items
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template,parent,false)
        viewHolder = ViewHolder(view,clickListener)
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return this.items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.artist?.text = item?.artist
        holder.album?.text = item?.album
        holder.song?.text = item?.song

        holder.photo?.setImageResource(item?.photo!!)

    }

    class ViewHolder(view: View,listener: ClickListener): RecyclerView.ViewHolder(view),View.OnClickListener{
        var photo:ImageView? = null
        var artist:TextView? = null
        var album:TextView? = null
        var song:TextView? = null

        var listener:ClickListener?= null

        init {
            photo = view.imageViewPhoto
            artist = view.textViewArtist
            album = view.textViewAlbum
            song = view.textViewSong

            this.listener = listener
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }

    }
}