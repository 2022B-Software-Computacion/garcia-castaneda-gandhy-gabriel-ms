import java.time.LocalDate
import java.util.Date

class Proveedor (
    var nombre: String,
    var fechaRegistro: LocalDate,
    var estaDisponible: Boolean,
    var direccion: String,
    var telefono: String
) {
    init {
        //nombre
        //fechaRegistro
    }

    override fun toString(): String {
        return "Proveedor('$nombre', $fechaRegistro, $estaDisponible, '$direccion', '$telefono')"
    }


}