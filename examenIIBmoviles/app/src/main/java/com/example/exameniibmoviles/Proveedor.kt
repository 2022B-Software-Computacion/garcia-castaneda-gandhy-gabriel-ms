package com.example.exameniibmoviles

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude

data class Proveedor (
    @Exclude @JvmField var id: String?,
    var nombre: String = "",
    var telefono: String = "",
    @Exclude @JvmField var productos: List<Producto> = emptyList()
): Parcelable {
    constructor( //Constructor (vacío)
    ) : this(null, "", "", emptyList())

    constructor( //Constructor de objetos
        nombre: String, telefono: String, productos: List<Producto>
    ) : this(null, nombre, telefono, productos)

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Producto)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeString(telefono)
        parcel.writeTypedList(productos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Proveedor> {
        override fun createFromParcel(parcel: Parcel): Proveedor {
            return Proveedor(parcel)
        }

        override fun newArray(size: Int): Array<Proveedor?> {
            return arrayOfNulls(size)
        }
    }
}