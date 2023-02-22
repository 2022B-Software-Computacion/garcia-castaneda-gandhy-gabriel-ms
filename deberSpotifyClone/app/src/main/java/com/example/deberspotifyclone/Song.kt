package com.example.deberspotifyclone

import android.os.Parcel
import android.os.Parcelable

class Song(
    var id: Int,
    var name: String?,
    var artist: String?
): Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ){

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(artist)
    }

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }
}