package com.example.deberspotifyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class PlaylistDetailActivity : AppCompatActivity() {
    lateinit var tvPlaylistArtists: TextView?
    lateinit var fabPlay: FloatingActionButton?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_detail)

        val playlistId = intent.getStringExtra("id")
        val ivPlaylist = findViewById<ImageView>(R.id.iv_playlist)
        val playlistImgUrl = intent.getStringExtra("img")
        val playlistName = intent.getStringExtra("name")
        val mainArtists = intent.getStringExtra("artists")

        Log.e("TAG", "playlist id is : " + playlistId)

        val tvPlaylistName = findViewById<TextView>(R.id.tv_playlist_name)
        val tvPlaylistArtists = findViewById<TextView>(R.id.tv_playlist_artists)
        val fabPlay = findViewById<FloatingActionButton>(R.id.fab_playlist_play)

        tvPlaylistName.text = playlistName
        tvPlaylistArtists.text = mainArtists

        fabPlay.setOnClickListener {

        }

        Picasso.get().load(playlistImgUrl).into(ivPlaylist)

        getPlaylistSongs(playlistId)
    }

    fun getPlaylistSongs(playlistId: String?) {
        val songsArray: ArrayList<Song>
    }
}