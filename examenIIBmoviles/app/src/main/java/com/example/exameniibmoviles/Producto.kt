package com.example.exameniibmoviles

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude

class Producto (
    @Exclude @JvmField var id: String?,
    var nombre: String = "",
    var precioUnit: Float = 0.00f,
    var stock: Int = 0,
    var descripcion: String = ""
): Parcelable {
    constructor( //Constructor vacío
    ) : this(null, "", 0.00f, 0, "")

    constructor( //Constructor de objetos
        nombre: String, precioUnit: Float, stock: Int, descripcion: String
    ) : this(null, nombre, precioUnit, stock, descripcion)

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat() ?: 0.00f,
        parcel.readInt() ?: 0,
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeFloat(precioUnit)
        parcel.writeInt(stock)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0;
    }

    companion object CREATOR : Parcelable.Creator<Producto>{
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}