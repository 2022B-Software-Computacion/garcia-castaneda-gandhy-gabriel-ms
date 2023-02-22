package com.example.deberspotifyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val song1 = Song(1, "Hysteria", "Muse")
    val song2 = Song(2, "Figure It Out", "Royal Blood")
    val song3 = Song(3, "Amsterdam", "Nothing But Thieves")
    val song4 = Song(4, "Won't Stand Down", "Muse")
    val song5 = Song(5, "Supermassive Black Hole", "Muse")
    val song6 = Song(6, "Ban All the Music", "Nothing But Thieves")
    val song7 = Song(7, "Do I Wanna Know?", "Arctic Monkeys")
    val song8 = Song(8, "Hyper Music - XX Anniversary RemiXX", "Muse")
    val song9 = Song(9, "Lonely Boy", "The Black Keys")
    val song10 = Song(10, "Go With The Flow", "Queens of the Stone Age")
    val song11 = Song(11, "Clint Eastwood", "Gorillaz")
    val song12 = Song(12, "Beggin'", "Måneskin")
    val song13 = Song(13, "Otherside", "Red Hot Chili Peppers")
    val song14 = Song(14, "Reptilia", "The Strokes")
    val song15 = Song(15, "GOSSIP (feat. Tom Morello)", "Måneskin")
    val song16 = Song(16, "(sic)", "Slipknot")
    val song17 = Song(17, "Eyeless", "Slipknot")
    val song18 = Song(18, "Wait and Bleed", "Slipknot")
    val song19 = Song(19, "Purity", "Slipknot")
    val song20 = Song(20, "Duality", "Slipknot")
    val song21 = Song(21, "All Out Life", "Slipknot")
    val song22 = Song(22, "B.Y.O.B", "System Of A Down")
    val song23 = Song(20, "Toxicity", "System Of A Down")
    val song24 = Song(20, "The Devil in I", "Slipknot")
    val song25 = Song(20, "Custer", "Slipknot")

    val globalArraySongs = arrayListOf(song1, song2, song3, song4, song5, song6, song7, song8, song9, song10, song11, song12, song13, song14, song15)

    val playlist1 = arrayListOf(song1, song2, song3, song4, song5, song6, song7, song8, song9)
    val playlist2 = arrayListOf(song10, song11, song12, song13, song14, song15)
    val playlist3 = arrayListOf(song1, song5, song4, song8, song16, song17, song18, song19, song20, song21)
    val playlist4 = arrayListOf(song12, song15, song5, song6, song19, song22, song23, song24, song25)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playlistArray = arrayListOf<Playlist>()
        playlistArray
            .add(Playlist(1, "Muse Mix", "Lo mejor de Muse, Nothing But Thieves y más", "Spotify", 50, 3.27f, "https://seed-mix-image.spotifycdn.com/v6/img/artist/12Chz98pHFMPJEknJQMWvI/en/default", "Muse, Nothing But Thieves, Arctic Monkeys", playlist1))
        playlistArray
            .add(Playlist(2, "Mix Rock", "Rage Against The Machine, Queens of Stone Age y más", "Gandhy García", 42, 3.11f, "https://media.resources.festicket.com/www/artists/NothingButThieves.jpeg", "RHCP, Muse, Nothing But Thieves, Radiohead", playlist2))
        playlistArray
            .add(Playlist(3, "Mix Varios", "Mix variado", "Gandhy García", 5, 3.11f, "https://upload.wikimedia.org/wikipedia/en/1/12/Muse_-_Will_of_the_People.png", "Muse, Slipknot, System Of A Down", playlist3))
        playlistArray
            .add(Playlist(4, "Mix para ti", "Rage Against The Machine, Queens of Stone Age y más", "Gandhy García", 42, 3.11f, "https://indiehoy.com/wp-content/uploads/2023/01/maneskin.jpg", "Maneskin, RHCP, Muse, Nothing But Thieves, Radiohead", playlist1))


        initRvFavMixes(playlistArray)
        initRvSimilars(playlistArray)
        initRvRecent(playlistArray)
        initSearchView()
    }

    fun initSearchView() {
        val searchEt: EditText = findViewById(R.id.et_search)

        searchEt.setOnEditorActionListener { tv, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchSongs(searchEt.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    fun searchSongs(query: String) {
        val i = Intent(this, SearchActivity::class.java)
        i.putExtra("searchQuery", query)
        i.putParcelableArrayListExtra("globalArraySongs", globalArraySongs)
        startActivity(i)
    }

    fun initRvFavMixes(playlistArray: ArrayList<Playlist>) {
        val rvPlaylists = findViewById<RecyclerView>(R.id.rv_mixes)

        val playlistAdapter = RecyclerViewAdapterPlaylist(playlistArray, this)
        rvPlaylists.adapter = playlistAdapter
        rvPlaylists.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        playlistAdapter.notifyDataSetChanged()
    }

    fun initRvSimilars(playlistArray: ArrayList<Playlist>) {
        val rvPlaylists = findViewById<RecyclerView>(R.id.rv_similars)

        val playlistAdapter = RecyclerViewAdapterPlaylist(playlistArray, this)
        rvPlaylists.adapter = playlistAdapter
        rvPlaylists.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        playlistAdapter.notifyDataSetChanged()
    }

    fun initRvRecent(playlistArray: ArrayList<Playlist>) {
        val rvPlaylists = findViewById<RecyclerView>(R.id.rv_recent)

        val playlistAdapter = RecyclerViewAdapterPlaylist(playlistArray, this)
        rvPlaylists.adapter = playlistAdapter
        rvPlaylists.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        playlistAdapter.notifyDataSetChanged()
    }
}