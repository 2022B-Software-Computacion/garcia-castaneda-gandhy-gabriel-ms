import java.time.LocalDate

class Proveedor (
    var id: Int,
    var nombre: String,
    var fechaRegistro: LocalDate,
    var estaDisponible: Boolean,
    var telefono: String,
    var productos: ArrayList<Int>
) {
    init {
        //nombre
        //fechaRegistro
    }

    constructor( //Segundo constructor (vacío)
    ) : this(0, "", LocalDate.parse("1999-11-05"), false, "", arrayListOf()) {
    }

    override fun toString(): String {
        return "$id,$nombre,$fechaRegistro,$estaDisponible,$telefono,$productos"
    }


}