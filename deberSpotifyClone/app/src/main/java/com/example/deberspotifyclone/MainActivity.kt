package com.example.deberspotifyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playlistArray = arrayListOf<Playlist>()
        playlistArray
            .add(Playlist(1, "Muse Mix", "Lo mejor de Muse, Nothing But Thieves y más", "Spotify", 50, 3.27f, "", "Muse, Nothing But Thieves, Radiohead"))
        playlistArray
            .add(Playlist(1, "Mix Rock", "Rage Against The Machine, Queens of Stone Age y más", "Gandhy García", 42, 3.11f, "", "RHCP, Muse, Nothing But Thieves, Radiohead"))

        initRvFavMixes(playlistArray)
        initRvSimilars(playlistArray)
        initSearchView()
    }

    fun initSearchView() {
        val searchEt: EditText = findViewById(R.id.et_search)

        searchEt.setOnEditorActionListener { tv, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchSongs(searchEt.text.toString())
                true
            }
            false
        }
    }

    fun searchSongs(query: String) {
        val i = Intent(this, SearchActivity::class.java)
        i.putExtra("searchQuery", query)
        startActivity(i)
    }

    fun initRvFavMixes(playlistArray: ArrayList<Playlist>) {
        val rvPlaylists = findViewById<RecyclerView>(R.id.rv_mixes)

        val playlistAdapter = RecyclerViewAdaptadorPlaylist(playlistArray, this)
        rvPlaylists.adapter = playlistAdapter
        //rvPlaylists.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        //rvPlaylists.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        playlistAdapter.notifyDataSetChanged()
    }

    fun initRvSimilars(playlistArray: ArrayList<Playlist>) {
        val rvPlaylists = findViewById<RecyclerView>(R.id.rv_similars)

        val playlistAdapter = RecyclerViewAdaptadorPlaylist(playlistArray, this)
        rvPlaylists.adapter = playlistAdapter
        //rvPlaylists.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        //rvPlaylists.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        playlistAdapter.notifyDataSetChanged()
    }

    fun initRvRecent(playlistArray: ArrayList<Playlist>) {
        val rvPlaylists = findViewById<RecyclerView>(R.id.rv_recent)

        val playlistAdapter = RecyclerViewAdaptadorPlaylist(playlistArray, this)
        rvPlaylists.adapter = playlistAdapter
        //rvPlaylists.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        //rvPlaylists.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        playlistAdapter.notifyDataSetChanged()
    }
}