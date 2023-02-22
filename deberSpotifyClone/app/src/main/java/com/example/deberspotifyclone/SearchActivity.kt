package com.example.deberspotifyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {
    private var globalSongs: ArrayList<Song>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchEdt = findViewById<EditText>(R.id.et_search)
        val query = intent.getStringExtra("searchQuery")

        intent.extras?.let { extras ->
            globalSongs = extras.getParcelableArrayList<Song>("globalArraySongs")
        }

        searchEdt.setText(query)

        searchEdt.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                getSongs(searchEdt.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }


        getSongs(query)
    }

    fun getSongs(searchQuery: String?){
        val rvSongs = findViewById<RecyclerView>(R.id.rv_songs)
        val songsResult: ArrayList<Song> = globalSongs?.filter { currentSong: Song ->
            val matchString: Boolean = searchQuery.equals(currentSong.artist, ignoreCase = true)
                    || searchQuery.equals(currentSong.name, ignoreCase = true)
            return@filter matchString
        } as ArrayList<Song>
        val rvSongsAdapter = RecyclerViewAdapterSong(songsResult, this)
        rvSongs.adapter = rvSongsAdapter

        rvSongsAdapter.notifyDataSetChanged()
    }
}