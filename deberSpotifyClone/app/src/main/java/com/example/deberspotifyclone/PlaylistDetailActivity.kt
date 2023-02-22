package com.example.deberspotifyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class PlaylistDetailActivity : AppCompatActivity() {
    private var playlistSongs: ArrayList<Song>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_detail)

        val playlistId = intent.getIntExtra("id", 0)
        val ivPlaylist = findViewById<ImageView>(R.id.iv_playlist)
        val playlistImgUrl = intent.getStringExtra("img")
        val playlistName = intent.getStringExtra("name")
        val mainArtists = intent.getStringExtra("artists")

        val tvPlaylistName = findViewById<TextView>(R.id.tv_playlist_name)
        val tvPlaylistArtists = findViewById<TextView>(R.id.tv_playlist_artists)
        val fabPlay = findViewById<FloatingActionButton>(R.id.fab_playlist_play)

        tvPlaylistName.text = playlistName
        tvPlaylistArtists.text = mainArtists

        fabPlay.setOnClickListener {

        }

        Picasso.get().load(playlistImgUrl).into(ivPlaylist)

        intent.extras?.let { extras ->
            playlistSongs = extras.getParcelableArrayList<Song>("songs")
        }

        getPlaylistSongs()
    }

    private fun getPlaylistSongs() {
        val rvSongs = findViewById<RecyclerView>(R.id.rv_album_songs)
        val rvSongsAdapter = RecyclerViewAdapterSong(playlistSongs!!, this)
        rvSongs.adapter = rvSongsAdapter

        rvSongsAdapter.notifyDataSetChanged()
    }
}
